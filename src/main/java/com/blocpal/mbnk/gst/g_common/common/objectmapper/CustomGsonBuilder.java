package com.blocpal.mbnk.gst.g_common.common.objectmapper;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.GsonBuilder;

public class CustomGsonBuilder {
	
	public static GsonBuilder getGsonBuilder ( ) {
		
		FieldNamingStrategy customPolicy = new CustomFieldNamePolicy();
		GsonBuilder gsonBuilder = new GsonBuilder();  		
		gsonBuilder.setFieldNamingStrategy(customPolicy);  		
		return gsonBuilder;
	}
	
	public static GsonBuilder getGsonBuilder(Boolean excludeTimestamp) {
		
		GsonBuilder builder = getGsonBuilder();
		if (excludeTimestamp) {
			ExclusionStrategy exclusionStrategy = new CustomExclusionStrategy();
			builder = builder.addSerializationExclusionStrategy(exclusionStrategy);
		}
		return builder;		
	}
}
