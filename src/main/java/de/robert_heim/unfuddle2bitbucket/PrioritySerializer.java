package de.robert_heim.unfuddle2bitbucket;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.robert_heim.unfuddle2bitbucket.model.Priority;

public class PrioritySerializer implements JsonSerializer<Priority> {
	public JsonElement serialize(Priority src, Type typeOfSrc,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getName());
	}
}
