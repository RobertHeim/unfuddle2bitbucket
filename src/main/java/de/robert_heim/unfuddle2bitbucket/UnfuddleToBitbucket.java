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

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import de.robert_heim.unfuddle2bitbucket.cli.HelpOptions;
import de.robert_heim.unfuddle2bitbucket.cli.RuntimeOptions;
import de.robert_heim.unfuddle2bitbucket.cli.UsagePrinter;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.DbJson;

/**
 * 1) JAXB is used to read the Unfuddle-Model from the input file (see
 * {@link de.robert_heim.unfuddle2bitbucket.model.unfuddle})
 * 
 * 2) The Model is converted the {@link DbJson}-Model of Bitbucket (see
 * {@link de.robert_heim.unfuddle2bitbucket.model.bitbucket}) using the
 * {@link BackupToDbJson} class
 * 
 * 3) Gson is used to print the json output from the {@link DbJson}-Model. It
 * uses the {@link Zipper} to place the output in a zip-archive as required by
 * Bitbucket.
 * 
 * @author Robert Heim
 */
public class UnfuddleToBitbucket {

	private static final String APPLICATION_NAME = "unfuddle2Bitbucket";
	private static final String VERSION_FILE = "/version.properties";

	public static void main(String[] args) {

		// create the command line parser
		CommandLineParser parser = new GnuParser();

		Options helpOptions = HelpOptions.create();
		Options runtimeOptions = RuntimeOptions.create(helpOptions);

		try {
			// parse the command line arguments for help or version
			HelpOrVersionRunner.parseAndRunCLIHelpOrVersion(parser,
					helpOptions, runtimeOptions, args, APPLICATION_NAME,
					VERSION_FILE);

			// parse the command line arguments for runtime
			ProgramRunner.parseAndRunCLIRuntime(parser, helpOptions,
					runtimeOptions, args);

		} catch (ParseException exp) {
			System.out.println("Error: " + exp.getMessage());
			UsagePrinter.printUsageAndHelp(APPLICATION_NAME, runtimeOptions,
					System.out);
		} catch (JAXBException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}

}