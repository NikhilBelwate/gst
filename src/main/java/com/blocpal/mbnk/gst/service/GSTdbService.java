package com.blocpal.mbnk.gst.service;

import com.blocpal.common.service.FirestoreService;
import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintEndpoints;
import com.blocpal.mbnk.gst.dao.models.TransactionLog;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class GSTdbService {
    @Inject
    private FirestoreService firestoreService;
    public List<TransactionLog> getPendingTransactions(){
        Firestore firestoreDB=firestoreService.getDb();
        List<TransactionLog> list=new ArrayList<>();
        ApiFuture<QuerySnapshot> future = firestoreDB.collection("txns").whereEqualTo("t","GST-CHALLAN").whereEqualTo("s", PaysprintEndpoints.PENDING).get();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            documents.forEach(queryDocumentSnapshot -> {
                list.add(queryDocumentSnapshot.toObject(TransactionLog.class));
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public boolean updateStatusInTxnLog(String txnId, String status){
        Firestore firestoreDB=firestoreService.getDb();
        ApiFuture<QuerySnapshot> future=firestoreDB.collection("txns")
                .whereEqualTo("t","GST-CHALLAN")
                .whereEqualTo("sp.tId",txnId).get();
        try {
            QueryDocumentSnapshot queryDocumentSnapshot=future.get().getDocuments().get(0);
            String ID=queryDocumentSnapshot.getId();
            TransactionLog txnLog=queryDocumentSnapshot.toObject(TransactionLog.class);
            txnLog.setS(status);
            //to perform update in txns collection
            firestoreDB.collection("txns").document(ID).set(txnLog);
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
