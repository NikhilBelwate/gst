package com.blocpal.mbnk.gst.exception;

import com.blocpal.common.exception.ServiceException;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GstException extends ServiceException implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer responseCode;
    private String message;

    public GstException(Integer responseCode, String message, Boolean status) {
        setStatusCode(responseCode);
        setMessage(message);
        setLogError(true);
        this.responseCode=responseCode;
        this.message=message;
    }
    public GstException(Integer responseCode, String message) {
        setStatusCode(responseCode);
        setMessage(message);
        setLogError(true);
        this.responseCode=responseCode;
        this.message=message;
    }

    public Map getExceptionDetails() {
        Map map=new HashMap<>();
        map.put("responseCode",responseCode);
        map.put("message",message);
        return map;
    }
}
