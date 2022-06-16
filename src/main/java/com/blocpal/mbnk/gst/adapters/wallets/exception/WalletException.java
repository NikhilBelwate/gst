package com.blocpal.mbnk.gst.adapters.wallets.exception;

import com.blocpal.mbnk.gst.g_common.ServiceException;

public class WalletException extends ServiceException {
	
	public WalletException (Integer code, String message) {
		this.setStatusCode(code);
		this.setMessage(message);
	}
}
