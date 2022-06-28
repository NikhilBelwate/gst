package com.blocpal.mbnk.gst.adapters.wallets.exception;

import com.blocpal.common.exception.ServiceException;
import com.blocpal.mbnk.gst.common.AppStatusCodes;

public class TxnInitiateFailed extends ServiceException {
    private static final String MESSAGE = "Initiate Transaction failed";

    public TxnInitiateFailed() {
        this.setStatusCode(AppStatusCodes.INITIATE_TRANSACTION_FAILED);
        this.setMessage(MESSAGE);
    }

    public TxnInitiateFailed(String message) {
        this.setStatusCode(AppStatusCodes.INITIATE_TRANSACTION_FAILED);
        this.setMessage(message);
    }
}
