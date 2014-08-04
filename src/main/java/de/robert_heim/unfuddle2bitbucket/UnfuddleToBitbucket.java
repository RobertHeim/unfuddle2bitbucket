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

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

import com.google.gson.GsonBuilder;

import de.robert_heim.unfuddle2bitbucket.model.DbJson;

public class UnfuddleToBitbucket {

	private static final String DEFAULT_PROPERTIES_FILE = "/config.properties";

	public static void main(String[] args) throws Exception {

		// TODO if (properties file given / present ) load it
		// else use default
		Properties properties = new Properties();
		InputStream in = UnfuddleToBitbucket.class
				.getResourceAsStream(DEFAULT_PROPERTIES_FILE);
		properties.load(in);
		in.close();

		BackupToModel bm = new BackupToModel(properties);
		DbJson dbJson = bm.convert("./backup.xml");

		GsonBuilder gson = new GsonBuilder().setPrettyPrinting();

		gson.registerTypeAdapter(Date.class, new DateTimeSerializer());

		System.out.println(gson.create().toJson(dbJson));
	}
}