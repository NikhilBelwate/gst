package com.blocpal.mbnk.gst.exception;

import com.google.gson.Gson;

public class GstException extends Exception{
    public GstException(String message) {
        super(message);
    }
    public GstException(Exception e) {
        super(e);
    }

    @Override
    public String getMessage() {
        return new Gson().toJson(this);
    }
}
