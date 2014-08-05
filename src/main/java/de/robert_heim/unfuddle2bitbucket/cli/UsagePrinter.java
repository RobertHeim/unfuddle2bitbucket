package de.robert_heim.unfuddle2bitbucket.cli;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class UsagePrinter {

	public static void printUsageAndHelp(final String applicationName,
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

}
