package de.robert_heim.unfuddle2bitbucket.cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class RuntimeOptions {
	// suppress static-access because commons-cli has a bad design of the
	// OptionBuilder in v1.2 and 1.3 is not released, yet. See
	// http://stackoverflow.com/questions/12466955/java-apache-cli-optionbuilder-not-working-as-builder-pattern
	@SuppressWarnings("static-access")
	public static Options create(Options helpOptions) {

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
								"The configuration file. See documentation at github for further information.")
						.hasArg().withArgName("FILE").create("c"));

		runOptions.addOption(OptionBuilder.withLongOpt("input-file")
				.withDescription("the backup.xml created by unfuddle").hasArg()
				.isRequired().withArgName("FILE").create("i"));

		runOptions.addOption(OptionBuilder.withLongOpt("output-file")
				.isRequired().withValueSeparator('=')
				.withDescription("the file to write the archive to").hasArg()
				.withArgName("FILE").create("o"));

		return runOptions;
	}
}
