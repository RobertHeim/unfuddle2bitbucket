package de.robert_heim.unfuddle2bitbucket.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.robert_heim.unfuddle2bitbucket.model.Status;

public class StatusSerializer implements JsonSerializer<Status> {
	public JsonElement serialize(Status src, Type typeOfSrc,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getName());
	}
}
