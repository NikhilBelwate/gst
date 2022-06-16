package com.blocpal.mbnk.gst.g_common.common.objectmapper;


import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class CustomExclusionStrategy implements ExclusionStrategy {
 
    public boolean shouldSkipField(FieldAttributes f) {
    	Boolean result = false;
    	if ( null != f.getAnnotation(DocumentId.class) ||
    			null != f.getAnnotation(Exclude.class)  ||
    				f.getDeclaredType().getTypeName().equals(Timestamp.class.getTypeName())) {
    		result = true;
    	}
    	return result;
    }
 
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }
}
