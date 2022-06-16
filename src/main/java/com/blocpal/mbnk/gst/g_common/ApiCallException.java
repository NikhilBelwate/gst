package com.blocpal.mbnk.gst.g_common;

public class ApiCallException extends ServiceException {

    public ApiCallException(Exception e) {
        this.setStatusCode(CommonAppStatusCodes.API_CALL_EXCEPTION);
        this.setMessage(e.getMessage());
    }

}
