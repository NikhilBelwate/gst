package com.blocpal.mbnk.gst.g_common;

public class AuthFailedException extends ServiceException {

    private static String MESSAGE = "Firebase uuid %1$s and the input userId %2$s doesnot match";

    public AuthFailedException(String message) {

        this.setStatusCode(CommonAppStatusCodes.AUTH_FAILED_EXCEPTION);
        this.setMessage(message);
    }

    public AuthFailedException (String uuid, String userId) {
        this.setStatusCode(CommonAppStatusCodes.AUTH_FAILED_EXCEPTION);
        this.setMessage(String.format(MESSAGE, uuid,userId));
    }
}
