package com.blocpal.mbnk.gst.adapters.db.dao;

import com.blocpal.common.service.FirestoreService;
import com.blocpal.common.utility.FirestoreUtility;
import com.blocpal.mbnk.gst.adapters.db.model.DBTransaction;
import com.blocpal.mbnk.gst.adapters.db.model.IntegrationCustInfo;
import com.blocpal.mbnk.gst.adapters.db.model.IntegrationServiceProviderInfo;
import com.blocpal.mbnk.gst.adapters.db.model.InternalUserInfo;
import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintConstant;
import com.blocpal.mbnk.gst.adapters.wallets.model.UserInfo;
import com.blocpal.mbnk.gst.dao.models.GSTData;
import com.blocpal.mbnk.gst.dao.models.TransactionLog;
import com.blocpal.mbnk.gst.exception.GstException;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import lombok.extern.slf4j.Slf4j;
import org.ietf.jgss.GSSException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintConstant.TRANSACTION_TYPE;
import static com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintEndpoints.walletConstant.PENDING;

@Slf4j
@Singleton
public class TransactionDAO {

	@Inject
	FirestoreService dbSrvc;
	Firestore db = null;
	FirestoreUtility dbUtility = null;

	private static final String TRANSACTION_COLLECTIONS = "txns";

	@PostConstruct
	public void initialize() throws IOException {
		log.info("Initializing  -> ");
		db = dbSrvc.getDb();
		dbUtility = new FirestoreUtility(db);
		log.info("Initialized  -> ");
	}

	public String createTradeTransaction(GSTData gstData,
										 UserInfo userInfo,
										 String serviceProviderId,
										 IntegrationCustInfo custInfo,
										 String status,
										 String walletId) {
		log.info("createTradeTransaction  -> ");
		DBTransaction transaction = new DBTransaction();

		transaction.setAmount(gstData.getAmount());
		transaction.setStatus(status);
		transaction.setType(TRANSACTION_TYPE);
		transaction.setSubType("CHALLAN");

		transaction.setDateCreated(Timestamp.now().toDate());
		transaction.setDateUpdated(Timestamp.now().toDate());

		// transaction.setQuote(executeTradeRequest.getQuote());

		InternalUserInfo internalUserInfo = new InternalUserInfo();
		internalUserInfo.setMobile(String.valueOf(userInfo.getProfile().getMobile()));
		internalUserInfo.setId(userInfo.getAuthId());
		internalUserInfo.setWalletId(walletId);

		IntegrationServiceProviderInfo serviceProviderInfo = new IntegrationServiceProviderInfo();
		serviceProviderInfo.setId(serviceProviderId);
		serviceProviderInfo.setGibTxnId(gstData.getGibTxnId());
		serviceProviderInfo.setReqData(gstData.toMap());

		transaction.setInternalUserInfo(internalUserInfo);
		transaction.setServiceProviderInfo(serviceProviderInfo);
		transaction.setCustInfo(custInfo);

		try {
			// Create txn in db
			log.info("Adding transaction in DB:" + transaction.toString());

			String txnId = gstData.getTxnId();

			if(txnId != null){
				dbUtility.create(TRANSACTION_COLLECTIONS, transaction,gstData.getTxnId());
			}
			else{
				txnId = dbUtility.add(TRANSACTION_COLLECTIONS, transaction);
			}

			log.info("Txn added in integrations successfully");
			return txnId;
		} catch (InterruptedException | ExecutionException e) {
			log.error("Error initiating txn in  Integrations ", e);
			throw new GstException(5007," Error initiating transaction in DB");
		}
	}

	public void updateTransaction(String txnId, String status, Map<String, Object> respData) {
		log.info("updateTransaction starting");

		try {
			// update txn in db
			DBTransaction txnData = dbUtility.get(TRANSACTION_COLLECTIONS, txnId, DBTransaction.class);
			IntegrationServiceProviderInfo serviceProviderInfo = txnData.getServiceProviderInfo();
			Map<String, Object> mergeData = new HashMap<>();
			mergeData.put("s", status);
			mergeData.put("uts", Timestamp.now().toDate());
			if (respData != null) {
				serviceProviderInfo.setRespData(respData);
			}
			mergeData.put("sp", serviceProviderInfo);
			dbUtility.merge(TRANSACTION_COLLECTIONS, mergeData, txnId);

			log.info("Txn added in integrations successfully");
		} catch (InterruptedException | ExecutionException e) {
			log.error("Error updating txn in integration ", e);
			throw new GstException(5007," Error initiating transaction in DB");
		}

		log.info("updateTransaction complete");
	}


	public DBTransaction getTransactionWithId(String txnId) {
		try {
			DBTransaction txnData = dbUtility.get(TRANSACTION_COLLECTIONS, txnId, DBTransaction.class);
			return txnData;
		} catch (InterruptedException | ExecutionException e) {
			log.error("Error fetching txn in integration ", e);
			throw new GstException(5008," Error fetching transaction from DB");
		}
	}
	public List<TransactionLog> getPendingTransactions(){
		List<TransactionLog> list=new ArrayList<>();
		ApiFuture<QuerySnapshot> future = db.collection("txns").whereEqualTo("t", TRANSACTION_TYPE)
				.whereEqualTo("s",PENDING)
				.get();
		try {
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			documents.forEach(queryDocumentSnapshot -> {
				TransactionLog tLog=queryDocumentSnapshot.toObject(TransactionLog.class);
				if(tLog.getTxnId()!=null)
					list.add(tLog);
			});
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	public String getTxnId(String gibTxnId){
		ApiFuture<QuerySnapshot> future = db.collection("txns").whereEqualTo("t", TRANSACTION_TYPE)
				.whereEqualTo("sp.gibTxnId",gibTxnId)
				.get();
		try {
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			if(documents.size()==1){
				TransactionLog tLog=documents.get(0).toObject(TransactionLog.class);
				if(tLog.getTxnId()!=null)
					return tLog.getTxnId();
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new GstException(3002,e.getMessage());
		}
		throw new GstException(3001,"No transaction found please initiate new inquiry");
	}
	public String getGibTxnId(String txnId){
		ApiFuture<QuerySnapshot> future = db.collection("txns").whereEqualTo("t", TRANSACTION_TYPE)
				.whereEqualTo("txnId",txnId)
				.get();
		try {
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			if(documents.size()==1){
				TransactionLog tLog=documents.get(0).toObject(TransactionLog.class);
				if(tLog.getSp().getGibTxnId()!=null)
					return tLog.getSp().getGibTxnId();
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new GstException(3002,e.getMessage());
		}
		throw new GstException(3003,"Service Provider transaction Id missing please initiate new inquiry");
	}
	public boolean updateStatusInTxnLog(String txnId, String status){
		ApiFuture<QuerySnapshot> future=db.collection("txns")
				.whereEqualTo("t", TRANSACTION_TYPE)
				.whereEqualTo("txnId",txnId).get();
		try {
			List<QueryDocumentSnapshot> list=future.get().getDocuments();
			if(list.size()==0)
				return false;
			QueryDocumentSnapshot queryDocumentSnapshot=future.get().getDocuments().get(0);
			String ID=queryDocumentSnapshot.getId();
			TransactionLog txnLog=queryDocumentSnapshot.toObject(TransactionLog.class);
			txnLog.setS(status);
			//to perform update in txns collection
			db.collection("txns").document(ID).set(txnLog);
			return true;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
	public TransactionLog getTransaction(String txnId){
		ApiFuture<DocumentSnapshot> future=db.collection("txns").document(txnId).get();
		try {
			DocumentSnapshot documentSnapshot=future.get();
			return documentSnapshot.toObject(TransactionLog.class);
		} catch (Exception e) {
			throw new GstException(2001,e.getMessage());
		}
	}
}
