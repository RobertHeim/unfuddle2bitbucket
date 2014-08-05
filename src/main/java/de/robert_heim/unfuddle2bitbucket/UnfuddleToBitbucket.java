/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014 Robert Heim <robert@robert-heim.de>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.robert_heim.unfuddle2bitbucket;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.robert_heim.unfuddle2bitbucket.model.DbJson;

public class UnfuddleToBitbucket {

	private static final String APPLICATION_NAME = "unfuddleToBitbucket";
	private static final String VERSION = "0.1b";

	public static void main(String[] args) throws Exception {

		// create the command line parser
		CommandLineParser parser = new GnuParser();

		Options helpOptions = createOptionsHelpVersion();
		Options runtimeOptions = createRuntimeOptions(helpOptions);

		CommandLine line = parser.parse(helpOptions, args, true);
		if (line.getOptions().length > 0) {
			if (line.hasOption("h")) {
				printUsageAndHelp(APPLICATION_NAME, runtimeOptions, System.out);
			}
			if (line.hasOption("v")) {
				System.out.println("version: " + VERSION);
			}
		} else {
			// create the Options
			Properties properties = new Properties();
			// parse the command line arguments
			try {
				line = parser.parse(runtimeOptions, args);

				if (line.hasOption("h")) {
					printUsageAndHelp(APPLICATION_NAME, runtimeOptions,
							System.out);
				} else {
					if (line.hasOption("c")) {
						String configFile = line.getOptionValue("c");
						InputStream in = new FileInputStream(configFile);
						properties.load(in);
						in.close();
					}

					String inputFileName = line.getOptionValue("i");
					String outputFileName = line.getOptionValue("o");

					boolean writeToOutputFile = true;
					if (!line.hasOption("fw") && fileExists(outputFileName)) {
						writeToOutputFile = false;
					}

					if (!writeToOutputFile) {
						System.err
								.println("Output file does exist. Please provide a path to a file that does not exist or provide the -fw (--force-write) option.");
					} else {

						Gson gson = createAndConfigureGson(line.hasOption("p"));
						UsernameMap usernameMap = null;
						if (!line.hasOption("u")) {
							usernameMap = new UsernameMap();
						} else {
							String usernameMapFileName = line
									.getOptionValue("u");
							if (!fileExists(usernameMapFileName)) {
								System.err
										.println("The specified user-map-file ('"
												+ usernameMapFileName
												+ "') does not exist! Quitting.");
								System.exit(1);
							} else {
								FileInputStream userMapInputStream = new FileInputStream(
										new File(usernameMapFileName));

								String userMapString = IOUtils.toString(
										userMapInputStream, "UTF-8");

								usernameMap = gson.fromJson(userMapString,
										UsernameMap.class);
							}
						}
						BackupToModel bm = new BackupToModel(properties,
								usernameMap);
						DbJson dbJson = bm.convert(inputFileName);

						String result = gson.toJson(dbJson);
						// print to outputFile
						PrintWriter writer = new PrintWriter(outputFileName,
								"UTF-8");
						writer.write(result);
						writer.close();
						System.out.println("Done. You can find the result in "
								+ outputFileName);
					}

				}
			} catch (ParseException exp) {
				System.out.println("Error: " + exp.getMessage());
				printUsageAndHelp(APPLICATION_NAME, runtimeOptions, System.out);
			}
		}
	}

	private static Gson createAndConfigureGson(boolean prettyPrint) {
		GsonBuilder gson = new GsonBuilder();
		if (prettyPrint) {
			gson.setPrettyPrinting();
		}
		gson.registerTypeAdapter(Date.class, new DateTimeSerializer());
		return gson.create();

	}

	private static void printUsageAndHelp(final String applicationName,
			final Options options, final OutputStream out) {
		HelpFormatter formatter = new HelpFormatter();

		final PrintWriter writer = new PrintWriter(out);

		int printedRowWidth = 100;
		String header = "Options:";
		int spacesBeforeOption = 4;
		int spacesBeforeOptionDescription = 3;
		String footer = "";
		boolean displayUsage = true;

		formatter.printHelp(writer, printedRowWidth, applicationName, header,
				options, spacesBeforeOption, spacesBeforeOptionDescription,
				footer, displayUsage);
		writer.close();
	}

	// suppress static-access because commons-cli has a bad design of the
	// OptionBuilder in v1.2 and 1.3 is not released, yet. See
	// http://stackoverflow.com/questions/12466955/java-apache-cli-optionbuilder-not-working-as-builder-pattern
	@SuppressWarnings("static-access")
	private static Options createOptionsHelpVersion() {
		Options options = new Options();
		options.addOption(OptionBuilder.withLongOpt("help")
				.withDescription("print this help text").create("h"));
		options.addOption(OptionBuilder.withLongOpt("version")
				.withDescription("print the version").create("v"));
		return options;
	}

	// suppress static-access because commons-cli has a bad design of the
	// OptionBuilder in v1.2 and 1.3 is not released, yet. See
	// http://stackoverflow.com/questions/12466955/java-apache-cli-optionbuilder-not-working-as-builder-pattern
	@SuppressWarnings("static-access")
	private static Options createRuntimeOptions(Options helpOptions) {

		Options runOptions = new Options();

		// add help and version, so they get printed when printing the help text
		for (Object o : helpOptions.getOptions()) {
			runOptions.addOption((Option) o);
		}

		runOptions
				.addOption(OptionBuilder
						.withLongOpt("pretty-print")
						.withDescription(
								"print the json in readable format instead of minimizing the output")
						.create("p"));

		runOptions.addOption(OptionBuilder.withLongOpt("force-write")
				.withDescription("overwrite the output file if it exists")
				.create("fw"));

		runOptions
				.addOption(OptionBuilder
						.withLongOpt("config-file")
						.withDescription(
								"use FILE instead of standard config\n"
										+ "the FILE can contain:\n"
										+ "|default.kind=[bug | enhancement | proposal | task]\n"
										+ "|\tif the given value is not within that list, bug is used\n"
										+ "|default.assignee=[auto_first | name | (can be null)]\n"
										+ "|\tauto_first: takes the first person found\n"
										+ "|\tname:\tthe given username is set as default\n"
										+ "|\t\tif it does not exist in the people-tag\n"
										+ "|\t\tno user is set as default assignee\n"
										+ "|\tnull / not specified: no default assignee\n"
										+ "|default.component=... analogous to default.assignee\n"
										+ "|\tif name is provided, the component must exist in the backup\n"
										+ "|default.milestone=... analogous to default.assignee\n"
										+ "|\tif name is provided, the milestone must exist in the backup\n"
										+ "|default.version=... analogous to default.assignee\n"
										+ "|\tif name is provided, the version must exist in the backup\n"
										+ "|").hasArg().withArgName("FILE")
						.create("c"));

		runOptions.addOption(OptionBuilder.withLongOpt("input-file")
				.withDescription("the backup.xml created by unfuddle").hasArg()
				.isRequired().withArgName("FILE").create("i"));

		runOptions.addOption(OptionBuilder.withLongOpt("output-file")
				.isRequired().withValueSeparator('=')
				.withDescription("the file to write the JSON-output to")
				.hasArg().withArgName("FILE").create("o"));

		runOptions
				.addOption(OptionBuilder
						.withLongOpt("user-mapping")
						.withDescription(
								"a JSON file that maps the users of Unfuddle to those of Bitbucket\n"
										+ "Format example:\n"
										+ "|{\n"
										+ "|  \"userMap\":{\n"
										+ "|    \"unfuddleUser1\":\"bitbucketUser1\",\n"
										+ "|    \"unfuddleUser2\":\"bitbucketUser2\",\n"
										+ "|    \"unfuddleUser...\":\"bitbucketUser...\",\n"
										+ "|    \"unfuddleUserN\":\"bitbucketUserN\"\n"
										+ "|  }\n" + "|}").hasArg()
						.withArgName("FILE").create("u"));

		return runOptions;
	}

	private static boolean fileExists(String filename) {
		return new File(filename).exists();
	}
}