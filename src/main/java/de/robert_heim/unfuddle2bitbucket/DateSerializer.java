package de.robert_heim.unfuddle2bitbucket;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateSerializer implements JsonSerializer<Date> {

	private DateFormat iso8601Format = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");

	public JsonElement serialize(Date src, Type typeOfSrc,
			JsonSerializationContext context) {
		return new JsonPrimitive(iso8601Format.format(src));
	}
}
