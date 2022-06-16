package com.blocpal.mbnk.gst.adapters.wallets.exception;

import com.blocpal.mbnk.gst.common.AppStatusCodes;
import com.blocpal.mbnk.gst.g_common.ServiceException;

public class TxnUpdateFailed extends ServiceException {

    private static final String MESSAGE = "Update Transaction failed";

    public TxnUpdateFailed() {
        this.setStatusCode(AppStatusCodes.UPDATE_TRANSACTION_FAILED);
        this.setMessage(MESSAGE);
    }

    public TxnUpdateFailed(String message) {
        this.setStatusCode(AppStatusCodes.UPDATE_TRANSACTION_FAILED);
        this.setMessage(message);
    }
}
