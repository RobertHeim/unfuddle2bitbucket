package de.robert_heim.unfuddle2bitbucket;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Kind;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Priority;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Status;
import de.robert_heim.unfuddle2bitbucket.serializer.DateSerializer;
import de.robert_heim.unfuddle2bitbucket.serializer.KindSerializer;
import de.robert_heim.unfuddle2bitbucket.serializer.PrioritySerializer;
import de.robert_heim.unfuddle2bitbucket.serializer.StatusSerializer;

public class GsonConfigurer {
	public static Gson createConfiguredGson(boolean prettyPrint) {
		GsonBuilder gson = new GsonBuilder();
		if (prettyPrint) {
			gson.setPrettyPrinting();
		}
		gson.registerTypeAdapter(Date.class, new DateSerializer());
		gson.registerTypeAdapter(Kind.class, new KindSerializer());
		gson.registerTypeAdapter(Status.class, new StatusSerializer());
		gson.registerTypeAdapter(Priority.class, new PrioritySerializer());

		return gson.create();

	}
}
