package de.robert_heim.unfuddle2bitbucket;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.robert_heim.unfuddle2bitbucket.cli.UsagePrinter;

public class HelpOrVersionRunner {

	public static void parseAndRunCLIHelpOrVersion(CommandLineParser parser,
			Options helpOptions, Options runtimeOptions, String[] args,
			String applicationName, String versionFile) throws ParseException,
			IOException {
		CommandLine line = parser.parse(helpOptions, args, true);
		if (line.getOptions().length > 0) {
			if (line.hasOption("h")) {
				UsagePrinter.printUsageAndHelp(applicationName, runtimeOptions,
						System.out);
				System.exit(0);
			}
			if (line.hasOption("v")) {
				Properties properties = new Properties();
				InputStream in = UnfuddleToBitbucket.class
						.getResourceAsStream(versionFile);
				properties.load(in);
				in.close();
				System.out.println("version: "
						+ properties.getProperty("version"));
				System.exit(0);
			}
		}
	}

}
