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

					// validate that block-size has been set
					if (line.hasOption("block-size")) { // print the value of
														// block-size
						System.out.println(line.getOptionValue("block-size"));
					}

					String inputFile = line.getOptionValue("i");
					String outputFile = line.getOptionValue("o");
					
					BackupToModel bm = new BackupToModel(properties);
					DbJson dbJson = bm.convert(inputFile);

					GsonBuilder gson = new GsonBuilder();
					if (line.hasOption("p")) {
						gson.setPrettyPrinting();
					}
					gson.registerTypeAdapter(Date.class,
							new DateTimeSerializer());

					System.out.println(gson.create().toJson(dbJson));
					// TODO print to outputFile
				}
			} catch (ParseException exp) {
				System.out.println("Error: " + exp.getMessage());
				printUsageAndHelp(APPLICATION_NAME, runtimeOptions, System.out);
			}
		}
	}

	private static void printUsageAndHelp(final String applicationName,
			final Options options, final OutputStream out) {
		HelpFormatter formatter = new HelpFormatter();

		final PrintWriter writer = new PrintWriter(out);

		int printedRowWidth = 80;
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

		runOptions.addOption(OptionBuilder
				.withLongOpt("config-file")
				.withDescription(
						"use FILE instead of standard config.properties")
				.hasArg().withArgName("FILE").create("c"));

		runOptions.addOption(OptionBuilder.withLongOpt("input-file")
				.withDescription("the backup.xml created by unfuddle").hasArg()
				.isRequired().withArgName("FILE").create("i"));

		runOptions.addOption(OptionBuilder.withLongOpt("output-file")
				.isRequired().withValueSeparator('=')
				.withDescription("the file to write the JSON-output to")
				.hasArg().withArgName("FILE").create("o"));

		return runOptions;
	}
}