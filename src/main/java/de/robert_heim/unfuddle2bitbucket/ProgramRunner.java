package de.robert_heim.unfuddle2bitbucket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import de.robert_heim.unfuddle2bitbucket.model.ConfigJson;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.DbJson;

public class ProgramRunner {
	public static void parseAndRunCLIRuntime(CommandLineParser parser,
			Options helpOptions, Options runtimeOptions, String[] args)
			throws ParseException, IOException, JAXBException {
		CommandLine line = parser.parse(runtimeOptions, args);

		String inputFileName = line.getOptionValue("i");
		fileExistsOrSystemExit(inputFileName);

		String outputFileName = line.getOptionValue("o");
		if (!line.hasOption("fw") && fileExists(outputFileName)) {
			System.err
					.println("Output file does exist. Please provide a path to a file that does not exist or provide the -fw (--force-write) option.");
			System.exit(1);
		}

		Gson gson = GsonConfigurer.createConfiguredGson(line.hasOption("p"));

		ConfigJson configJson = createConfig(line, gson);

		BackupToDbJson bm = new BackupToDbJson(configJson);
		DbJson dbJson = bm.convert(inputFileName);

		String result = gson.toJson(dbJson);
		// print to outputFile
		Zipper.createZipArchive(outputFileName, result);
		System.out
				.println("Done. You can find the result in " + outputFileName);
	}

	private static ConfigJson createConfig(CommandLine line, Gson gson)
			throws IOException {
		if (line.hasOption("c")) {
			// read configfile
			String configFilename = line.getOptionValue("c");
			fileExistsOrSystemExit(configFilename);

			FileInputStream configInputStream = new FileInputStream(new File(
					configFilename));
			String configString = IOUtils.toString(configInputStream, "UTF-8");
			try {
				return gson.fromJson(configString, ConfigJson.class);
			} catch (JsonSyntaxException e) {
				System.err.println("Cannot parse config-file.");
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
		// empty config
		System.out
				.println("Warning: no config-file specified, using default configuration.");
		return new ConfigJson();
	}

	private static void fileExistsOrSystemExit(String filename) {
		if (!fileExists(filename)) {
			System.err.println("The file '" + filename
					+ "' does not exist. Exit.");
			System.exit(1);
		}
	}

	private static boolean fileExists(String filename) {
		return new File(filename).exists();
	}
}
