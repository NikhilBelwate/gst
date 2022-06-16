package com.blocpal.mbnk.gst.g_common;

public class DatabaseException extends ServiceException{
    public DatabaseException(Exception e) {
        this.setStatusCode(CommonAppStatusCodes.FIRESTORE_EXCEPTION);
        this.setMessage(e.getMessage());
    }
}
