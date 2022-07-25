package com.blocpal.mbnk.gst.dao.service.impl;

import com.blocpal.common.utility.QueryBuilder;
import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintEndpoints;
import com.blocpal.mbnk.gst.dao.models.TransactionLog;
import com.blocpal.mbnk.gst.dao.service.TransactionLogsDaoService;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Singleton
public class TransactionLogsDaoServiceImpl extends GenericDaoServiceImpl<TransactionLog>
		implements TransactionLogsDaoService {

	/**
	 * Gets the failed txns.
	 *
	 * @return the failed txns
	 * @throws InterruptedException the interrupted exception
	 * @throws ExecutionException the execution exception
	 */
	@Override
	public Map<String,TransactionLog> getPendingTxns(String service)
			throws InterruptedException, ExecutionException {
		List<QueryDocumentSnapshot> documents = null;
		Map<String,TransactionLog> logs = new HashMap<>();
		if(service.equalsIgnoreCase("lic")) {
			documents = findByLic(
					PaysprintEndpoints.STATUS,
					PaysprintEndpoints.PENDING);
		} else {
			documents = findByCC(
					PaysprintEndpoints.STATUS,
					PaysprintEndpoints.PENDING);
		}

		for (QueryDocumentSnapshot document : documents) {
			TransactionLog log = document.toObject(TransactionLog.class);
			logs.put(document.getId(), log);
		}
		return logs;
	}
	
	/**
	 * Gets the.
	 *

	 * @param doc the doc
	 * @return the transaction log
	 * @throws InterruptedException the interrupted exception
	 * @throws ExecutionException the execution exception
	 */
	public TransactionLog get(String doc) 
			throws InterruptedException, ExecutionException {
		
		DocumentReference query = dbUtility.getDocumentReference(PaysprintEndpoints.TXNS, doc);
		DocumentSnapshot document = query.get().get();
		TransactionLog log = document.toObject(TransactionLog.class);
		return log;
	}
	
	private List<QueryDocumentSnapshot> findByLic(String field, String value)
			throws InterruptedException, ExecutionException {
		QueryBuilder query = QueryBuilder.create(db, PaysprintEndpoints.TXNS)
				.whereEqualTo("t", "lic-paysprint")
				.whereEqualTo(field, value);
		return query.execute();
	}

	private List<QueryDocumentSnapshot> findByCC(String field, String value)
			throws InterruptedException, ExecutionException {
		QueryBuilder query = QueryBuilder.create(db, PaysprintEndpoints.TXNS)
				.whereEqualTo("t", "CREDITCARDPAY")
				.whereEqualTo(field, value);
		return query.execute();
	}
}
