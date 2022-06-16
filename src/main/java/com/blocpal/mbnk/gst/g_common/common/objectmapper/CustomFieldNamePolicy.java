package com.blocpal.mbnk.gst.g_common.common.objectmapper;

import com.google.cloud.firestore.annotation.PropertyName;
import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

public class CustomFieldNamePolicy implements FieldNamingStrategy{

	 @Override
    public String translateName(Field f) {
    	String result = f.getName();
    	if (f.isAnnotationPresent(PropertyName.class)) {
    		PropertyName propName = f.getAnnotation(PropertyName.class);
    		result = propName.value();
    	}
    		
        return result;
    }
}
