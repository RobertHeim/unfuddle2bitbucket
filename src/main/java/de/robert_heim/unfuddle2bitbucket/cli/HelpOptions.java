package de.robert_heim.unfuddle2bitbucket.cli;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class HelpOptions {
	// suppress static-access because commons-cli has a bad design of the
	// OptionBuilder in v1.2 and 1.3 is not released, yet. See
	// http://stackoverflow.com/questions/12466955/java-apache-cli-optionbuilder-not-working-as-builder-pattern
	@SuppressWarnings("static-access")
	public static Options create() {
		Options options = new Options();
		options.addOption(OptionBuilder.withLongOpt("help")
				.withDescription("print this help text").create("h"));
		options.addOption(OptionBuilder.withLongOpt("version")
				.withDescription("print the version").create("v"));
		return options;
	}
}
