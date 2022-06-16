package com.blocpal.mbnk.gst.exception;

import com.google.gson.Gson;

public class FastTagException extends Exception{
    public FastTagException(String message) {
        super(message);
    }
    public FastTagException(Exception e) {
        super(e);
    }

    @Override
    public String getMessage() {
        return new Gson().toJson(this);
    }
}
