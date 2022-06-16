package com.blocpal.mbnk.gst.g_common;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

class FirestoreBatchOps<T> {
	
	private static final Integer BATCH_SIZE = 500;
	private static final Logger logger = LoggerFactory.getLogger(FirestoreBatchOps.class);
	
	public static enum Operation {
		CREATE,
		SET,
		MERGE,
		UPDATE,
		DELETE
	} 
	
	FirestoreBatchOps (Operation op, Boolean async){
		this.op = op;
		this.async = async;
	}
	
	protected Operation op;
	protected Boolean async;
		
	
	/*
	 * Creates batches
	 */
	public static <T> List<List<T>> getBatches(List<T> collection,int batchSize){
	    int i = 0;
	    List<List<T>> batches = new ArrayList<List<T>>();
	    while(i<collection.size()){
	        int nextInc = Math.min(collection.size()-i,batchSize);
	        List<T> batch = collection.subList(i,i+nextInc);
	        batches.add(batch);
	        i = i + nextInc;
	    }

	    return batches;
	}
	
	/*
	 * Write multiple objects in a batch
	 */
	public  List<String> process(Firestore db, 
			List<DocumentReference> docRefs, 
			List<T> datas ) throws InterruptedException, ExecutionException {
		
		logger.info("---->Start Batch Operation "+ op);
		
		List<String> result = new ArrayList<String>();
		
		// Get Batches
		List<List<DocumentReference>> docRefBatches = getBatches(
				docRefs,BATCH_SIZE);
		
		List<List<T>> dataBatches = null;		
		if (!op.equals(Operation.DELETE))
			dataBatches = getBatches(datas,BATCH_SIZE);
		
		// Write in multiple batches
		List<ApiFuture<List<WriteResult>>> listFutureList = null;
		listFutureList = new ArrayList<ApiFuture<List<WriteResult>>>();
		for (int batchIndex = 0; batchIndex < docRefBatches.size(); batchIndex++) {			
			List<T> currDatas = null; 
			if (!op.equals(Operation.DELETE))
				currDatas = dataBatches.get(batchIndex);
			List<DocumentReference> currDocRefs = docRefBatches.get(batchIndex);			
			WriteBatch batch = db.batch();		
			for (int index=0; index< currDocRefs.size(); index++) {
				T data = null;
				if (!op.equals(Operation.DELETE))
					data = currDatas.get(index);
				DocumentReference docRef = currDocRefs.get(index);
				
				operation(batch, docRef, data);				
				result.add(docRef.getId());
			}						
			ApiFuture<List<WriteResult>> futureList = batch.commit();
			listFutureList.add(futureList);
		}
		if (!async) {
			for (ApiFuture<List<WriteResult>> futureList:listFutureList) {
				futureList.get();
			}
		}
		logger.info("<---End batch Operation");
		return result;
	}
	
	public void  operation(WriteBatch batch, DocumentReference docRef, 
			T data) {
		
		if (op.equals(Operation.CREATE)) 
			batch.create(docRef, data);
		else if(op.equals(Operation.SET)) 
			batch.set(docRef, data);
		else if (op.equals(Operation.MERGE)) 
			batch.set(docRef, data, SetOptions.merge());			
		else if (op.equals(Operation.DELETE))
			batch.delete(docRef);		
		else {
			logger.warn("Operation "+op + " not supported ");
		}
	}
}
