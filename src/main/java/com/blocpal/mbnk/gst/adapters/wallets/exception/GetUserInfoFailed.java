package com.blocpal.mbnk.gst.adapters.wallets.exception;


import com.blocpal.common.exception.ServiceException;
import com.blocpal.mbnk.gst.common.AppStatusCodes;

public class GetUserInfoFailed extends ServiceException {

    private static final String MESSAGE = "Get User Info Failed";

    public GetUserInfoFailed() {
        this.setStatusCode(AppStatusCodes.GET_USER_INFO_FAILED);
        this.setMessage(MESSAGE);
    }

    public GetUserInfoFailed(String message) {
        this.setStatusCode(AppStatusCodes.GET_USER_INFO_FAILED);
        this.setMessage(message);
    }
}