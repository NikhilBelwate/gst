package com.blocpal.mbnk.gst.g_common;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

class FirestoreBatchOpsMap extends FirestoreBatchOps<Map<String,Object>> {
	
	private static final Logger logger = LoggerFactory.getLogger(FirestoreBatchOps.class);
	
	FirestoreBatchOpsMap (Operation op, Boolean async){
		super(op,async);
	}
	
	@Override
	public  void  operation(WriteBatch batch, DocumentReference docRef,
			Map<String,Object> data) {
		
		if (op.equals(Operation.CREATE)) 
			batch.create(docRef, data);
		else if(op.equals(Operation.SET)) 
			batch.set(docRef, data);
		else if (op.equals(Operation.MERGE)) 
			batch.set(docRef, data, SetOptions.merge());			
		else if (op.equals(Operation.UPDATE))
			batch.update(docRef, data);
		else if (op.equals(Operation.DELETE))
			batch.delete(docRef);
		else 
			logger.warn("Operation "+op + " not supported ");		
	}
}
