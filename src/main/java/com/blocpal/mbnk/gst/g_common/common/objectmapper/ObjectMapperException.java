package com.blocpal.mbnk.gst.g_common.common.objectmapper;


import com.blocpal.mbnk.gst.g_common.CommonAppStatusCodes;
import com.blocpal.mbnk.gst.g_common.ServiceException;

public class ObjectMapperException extends ServiceException {
	
	
	private static String MESSAGE = "Object of type %s not supported";
	
	public ObjectMapperException(String message) {
		
		this.setStatusCode(CommonAppStatusCodes.OBJECT_MAPPER_EXCEPTION);
		this.setMessage(message);
	}
}
