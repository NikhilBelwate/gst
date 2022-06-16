package com.blocpal.mbnk.gst.g_common;

public class AuthHeaderFormatException extends ServiceException {

    private static String MESSAGE = "Authorization Header value is not in correct form";

    public AuthHeaderFormatException() {

        this.setStatusCode(CommonAppStatusCodes.AUTH_HEADER_FORMAT_EXCEPTION);
        this.setMessage(MESSAGE);
    }
}
