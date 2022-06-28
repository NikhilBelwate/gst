package com.blocpal.mbnk.gst.adapters.wallets.exception;


import com.blocpal.common.exception.ServiceException;
import com.blocpal.mbnk.gst.common.AppStatusCodes;

public class GetUserKycDocImageFailed extends ServiceException {

    private static final String MESSAGE = "Get user KYC document image failed";

    public GetUserKycDocImageFailed() {
        this.setStatusCode(AppStatusCodes.GET_USER_KYC_DOC_IMAGE_FAILED);
        this.setMessage(MESSAGE);
    }
}