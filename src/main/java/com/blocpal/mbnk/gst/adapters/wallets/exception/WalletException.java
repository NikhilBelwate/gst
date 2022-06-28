package com.blocpal.mbnk.gst.adapters.wallets.exception;


import com.blocpal.common.exception.ServiceException;

public class WalletException extends ServiceException {
	
	public WalletException (Integer code, String message) {
		this.setStatusCode(code);
		this.setMessage(message);
	}
}
