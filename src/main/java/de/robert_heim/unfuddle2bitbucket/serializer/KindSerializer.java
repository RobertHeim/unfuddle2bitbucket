package de.robert_heim.unfuddle2bitbucket.serializer;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Kind;

public class KindSerializer implements JsonSerializer<Kind> {
	public JsonElement serialize(Kind src, Type typeOfSrc,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getName());
	}
}
