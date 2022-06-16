package com.blocpal.mbnk.gst.g_common.common.objectmapper;

import com.google.cloud.Timestamp;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CustomTimestampSerializer implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {
 
	@Override
	public JsonElement serialize(Timestamp src, Type typeOfSrc, 
			JsonSerializationContext context) {
		
		 return new JsonPrimitive(src.toString());
	}

	@Override
	public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		 return (Timestamp.parseTimestamp(json.getAsString()));
	}
}