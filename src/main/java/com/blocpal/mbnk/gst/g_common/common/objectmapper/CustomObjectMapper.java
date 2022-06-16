package com.blocpal.mbnk.gst.g_common.common.objectmapper;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Blob;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.Exclude;
import com.google.cloud.firestore.annotation.PropertyName;
import com.google.firestore.v1.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

public class CustomObjectMapper {

	private static final Logger logger = LoggerFactory.getLogger(CustomObjectMapper.class);
	
	/*
	 * Populate Map using reflection, populate only non null values
	 */
	public static Map<String, Object> toMap(Object obj) {

		Map<String, Object> result = new HashMap<String, Object>();
		Class<?> curClass = obj.getClass();
		while (null != curClass) {
			Field fields[] = curClass.getDeclaredFields();
			for (int index = 0; index < fields.length; index++) {
				Field f = fields[index];
				String name = translateName(f);
				if (!skipField(f)) {
					try {
						f.setAccessible(true);
						Object value = f.get(obj);
						if (null != value)
							result.put(name,toObject(value));
					} catch (IllegalArgumentException | 
							IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				else {
					logger.info("Skipping field:"+name);
				}
			}
			curClass = curClass.getSuperclass();
		}		
		
		logger.info("Custom Object Mapper map:"+result);
		return result;
	}
	/*
	 *Referred com.google.cloud.firestore.CustomClassMapper
	 */	 
	public static Object toObject(Object obj) {
		
		logger.info("Class Type+"+obj.getClass().getTypeName());
		
		// Number
		if (obj instanceof Number) {
			if (obj instanceof Long || obj instanceof Integer || 
					obj instanceof Double || obj instanceof Float) {
				return obj;
			} else if (obj instanceof BigDecimal) {
				return String.valueOf(obj);
			} else {
				String message = String.format("Numbers of type %s are not supported, "
						+ "please use an" + " int, long, float, double or BigDecimal",
						obj.getClass().getSimpleName());
				throw new ObjectMapperException(message);
			}
		}
		// String
		else if (obj instanceof String) {
			return obj;
		} 
		// Boolean
		else if (obj instanceof Boolean) {
			return obj;
		}
		// Character
		else if (obj instanceof Character) {
			throw new ObjectMapperException("Characters are not supported, "
					+ "please use Strings");
		}		 
		// Map
		else if (obj instanceof Map) {
			Map<String, Object> result = new HashMap<>();
			for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) obj).entrySet()) {
				Object key = entry.getKey();
				Object val =  entry.getValue();
				if (key instanceof String) {
					String keyString = (String) key;					
					if (null != val) {								
						if (val.getClass().isAnnotationPresent(MapMarker.class))
							val = toMap(val);
						result.put(keyString, val );
					}
				} else {
					throw new ObjectMapperException("Maps with non-string "
							+ "keys are not supported");
				}
			}
			return result;		      
		}		
		// List
		else if (obj instanceof Collection) {
			if (obj instanceof List) {
				List<Object> list = (List<Object>) obj;
				List<Object> result = new ArrayList<>(list.size());
				for (int i = 0; i < list.size(); i++) {
					Object val = list.get(i);
					if (null != val) {
						if (val.getClass().isAnnotationPresent(MapMarker.class))
							val = toMap(val);
						result.add(val );
					}
				}
				return result;
			}else {
				throw new ObjectMapperException("Serializing Collections is not"
						+ " supported, please use Lists instead");	
			}
		}
		// Array
		else if (obj.getClass().isArray()) {
			throw new ObjectMapperException("Serializing Arrays is not supported"
					+ ", please use Lists instead");
		} 
		// Enum
		else if (obj instanceof Enum) {
		    return ((Enum<?>) obj).name();
		}
		// Firestore Data types
		else if (obj instanceof Date
		        || obj instanceof Timestamp
		        || obj instanceof GeoPoint
		        || obj instanceof Blob
		        || obj instanceof DocumentReference
		        || obj instanceof FieldValue
		        || obj instanceof Value) {
		      return obj;
		}
		// Anything else
		else {
			Object result = obj;
			if (obj.getClass().isAnnotationPresent(MapMarker.class))
				result = toMap(obj);
			return result;
		}					
	}
	 
	/*
	 * Name based on property Name (Field)
	 */
	public static String translateName(Field f) {
		String name = f.getName();
		if (f.isAnnotationPresent(PropertyName.class)) {
			PropertyName propName = f.getAnnotation(PropertyName.class);
			name = propName.value();
		}
		return name;
	}
	
	/*
	 * Skip Fields tagged as @Exclude or @DocumentId
	 */
	public static Boolean skipField(Field f) {
				 
    	Boolean result = false;
    	if ( f.isAnnotationPresent(DocumentId.class) ||
    			f.isAnnotationPresent(Exclude.class)) {
    		result = true;
    	}
    	return result;		    
	}
}
