package com.blocpal.mbnk.gst.dao.service;



import com.blocpal.mbnk.gst.dao.models.TransactionLog;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * The Interface TransactionLogsDaoService.
 */
public interface TransactionLogsDaoService extends GenericDaoService<TransactionLog> {
	
	Map<String,TransactionLog> getPendingTxns(String service)
			throws InterruptedException, ExecutionException;

	TransactionLog get(String doc)
			throws InterruptedException, ExecutionException;

}
