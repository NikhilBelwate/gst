package com.blocpal.mbnk.gst.g_common;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirestoreUtility {

    private static final Logger logger = LoggerFactory.getLogger(FirestoreUtility.class);

    Firestore db = null;

    public FirestoreUtility(Firestore db) {
        this.db = db;
    }

    /*
     *  Get Document References
     */
    public  List<DocumentReference> getDocumentReferences(
            CollectionReference collRef,
            List<String> ids){

        List<DocumentReference> docRefs = new ArrayList<DocumentReference>();
        for (String id: ids) {
            DocumentReference docRef = null;
            if (null != id)
                docRef = collRef.document(id);
            else
                docRef = collRef.document();
            docRefs.add(docRef);
        }
        return docRefs;
    }

    /*
     * Get Document Reference
     */
    public List<DocumentReference> getDocumentReferences(List<String> collNames) {

        List<DocumentReference> docRefs = new ArrayList<DocumentReference>();
        for (String collName:collNames) {
            CollectionReference collRef = db.collection(collName);
            docRefs.add(collRef.document());
        }
        return docRefs;
    }

    /*
     * Get Document Reference
     */
    public  DocumentReference getDocumentReference(String collName) {

        CollectionReference collRef = db.collection(collName);
        return collRef.document();
    }

    public String getDocumentId (String collName) {
        CollectionReference collRef = db.collection(collName);
        return collRef.document().getId();
    }

    /*
     * Get Document Reference
     */
    public DocumentReference getDocumentReference(String collName,
                                                  String id) {

        CollectionReference collRef = db.collection(collName);
        DocumentReference docRef = null;
        if (null != id)
            docRef = collRef.document(id);
        else
            docRef = collRef.document();

        return docRef;
    }

    /*
     * Get a document in a collection
     */
    public <T> T get(String collName, String docId,Class<? extends T> type)
            throws InterruptedException, ExecutionException {

        T record = null;
        CollectionReference colRef = db.collection(collName);

        DocumentReference recordRef = colRef.document(docId);
        DocumentSnapshot recordSnapshot = recordRef.get().get();
        if (recordSnapshot.exists())
            record= recordSnapshot.toObject(type);
        return record;
    }

    public DocumentSnapshot getDocumentSnapshot(String collName, String docId)
            throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(collName);

        DocumentReference recordRef = colRef.document(docId);
        DocumentSnapshot recordSnapshot = recordRef.get().get();

        return recordSnapshot;
    }

    public Map<String,Object> get(String collName, String docId)
            throws InterruptedException, ExecutionException {

        Map<String,Object> record = null;
        CollectionReference colRef = db.collection(collName);

        DocumentReference recordRef = colRef.document(docId);
        DocumentSnapshot recordSnapshot = recordRef.get().get();
        if (recordSnapshot.exists())
            record= recordSnapshot.getData();
        return record;
    }

    /*
     * Get all documents in a collection
     */
    public <T> List<T> get(String collName,List<String> ids,Class<? extends T> type)
            throws InterruptedException, ExecutionException {

        List<T> result = new ArrayList<T>();
        CollectionReference collRef = db.collection(collName);
        List<DocumentReference> docRefs = getDocumentReferences(collRef, ids);

        ApiFuture<List<DocumentSnapshot>> docSnapshotsFuture = db.getAll(
                docRefs.toArray(new DocumentReference[docRefs.size()]));

        List<DocumentSnapshot> docSnapshots = docSnapshotsFuture.get();
        for (DocumentSnapshot docSnapshot:docSnapshots) {
            if (docSnapshot.exists())
                result.add(docSnapshot.toObject(type));
        }
        return result;
    }

    /*
     * Get all documents in a collection
     */
    public  List<String> getDocumentIds(String collName)
            throws InterruptedException, ExecutionException {

        List<String> ids = new ArrayList<String>();
        CollectionReference collRef = db.collection(collName);
        Iterable<DocumentReference> docRefs = collRef.listDocuments();
        docRefs.forEach(item-> ids.add(item.getId()));

        return ids;
    }

    /*
     * Get all documents in a collection
     */
    public <T> List<T> getAll(String collName, Class<? extends T> type)
            throws InterruptedException, ExecutionException {

        List<T> result = new ArrayList<T>();
        CollectionReference collRef = db.collection(collName);
        ApiFuture<QuerySnapshot> futureQuerySnapshot = collRef.get();
        QuerySnapshot querySnapshot = futureQuerySnapshot.get();

        List<QueryDocumentSnapshot> docSnapshots = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot queryDocSnapshot: docSnapshots){
            T record = queryDocSnapshot.toObject(type);
            result.add(record);
        }
        return result;
    }

    /*
     * Add a document to a collection
     */
    public <T> String add( String  collName,T data)
            throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(collName);
        ApiFuture<DocumentReference> futureResult = colRef.add(data);
        DocumentReference docRef = futureResult.get();
        return docRef.getId();
    }


    /*
     * Add a document to a collection
     */
    public <T> void addAsync(String  collName, T data)  {

        CollectionReference colRef = db.collection(collName);
        colRef.add(data);
    }

    /*
     * Create a document
     */
    public <T> String create(String colName,T data )
            throws InterruptedException, ExecutionException {

        DocumentReference docRef = getDocumentReference(colName);
        ApiFuture<WriteResult> futureResult = docRef.create(data);
        futureResult.get();
        return docRef.getId();
    }

    /*
     * Create a documnent
     */
    public <T> String create( String colName, T data, String id )
            throws InterruptedException, ExecutionException {

        DocumentReference docRef = getDocumentReference(colName, id);
        ApiFuture<WriteResult> futureResult = docRef.create(data);
        futureResult.get();
        return docRef.getId();
    }

    /*
     * Create a documnent
     */
    public <T> String createAsync( String collName, T data ){

        DocumentReference docRef = getDocumentReference(collName);
        docRef.create(data);
        return docRef.getId();
    }

    /*
     * Create a documnent
     */
    public <T> String createAsync( String collName,T data, String id ){

        DocumentReference docRef = getDocumentReference(collName, id);
        docRef.create(data);
        return docRef.getId();
    }

    /*
     * Write a document Asynchronously
     */
    public <T> String  write( String  collName,T data, String id )
            throws InterruptedException, ExecutionException {

        DocumentReference docRef = getDocumentReference(collName, id);
        ApiFuture<WriteResult> futureResult = docRef.set(data);
        futureResult.get();
        return docRef.getId();
    }
    /*
     * Set a document
     */
    public <T> String writeAsync(String colName, T data, String id ) {

        DocumentReference docRef = getDocumentReference( colName, id);
        docRef.set(data);
        return docRef.getId();
    }
    /*
     * Merge a document
     */
    public <T> void merge( String collName, Map<String,Object> data,String id )
            throws InterruptedException, ExecutionException {

        DocumentReference docRef = getDocumentReference(collName, id);
        ApiFuture<WriteResult> futureResult = docRef.set(data,SetOptions.merge());
        futureResult.get();
    }

    /*
     * Merge a document
     */
    public <T> void mergeAsync(String collName, Map<String,Object> data, String id ){

        DocumentReference docRef = getDocumentReference(collName, id);
        docRef.set(data,SetOptions.merge());
    }

    /*
     * Updates field in a document
     */
    public <T> void update(String collName, Map<String,Object> data,String id)
            throws InterruptedException, ExecutionException {

        DocumentReference docRef = getDocumentReference(collName, id);
        ApiFuture<WriteResult> futureResult = docRef.update(data);
        futureResult.get();
    }

    /*
     * Updates field in a document asyncrhonously
     */
    public <T> void updateAsync(String collName, Map<String,Object> data,
                                String id) throws InterruptedException, ExecutionException {

        DocumentReference docRef = getDocumentReference(collName, id);
        docRef.update(data);
    }

    /*
     * Deletes a document
     */
    public void delete(String collName, String id)
            throws InterruptedException, ExecutionException {

        DocumentReference docRef =getDocumentReference(collName,id);
        ApiFuture<WriteResult> result = docRef.delete();
        result.get();

    }

    /*
     * Create multiple objects in a batch
     */
    public <T> List<String> batchCreate( String  colName, List<T> datas)
            throws InterruptedException, ExecutionException {

        logger.debug("---->Start batchCreate()");

        //Document References
        List<DocumentReference> docRefs = new ArrayList<DocumentReference>();
        datas.forEach(data ->docRefs.add(getDocumentReference(colName)));

        // Batch Create
        List<String> result =batchCreate(docRefs,datas);

        logger.debug("<---End batchCreate()");
        return result;
    }
    /*
     * Create multiple objects in a batch
     */
    public <T> List<String> batchCreate( String  colName, List<T> datas,
                                         List<String> ids) throws InterruptedException, ExecutionException {

        logger.debug("---->Start batchCreate()");

        CollectionReference colRef = db.collection(colName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        List<String> result =batchCreate(docRefs,datas);

        logger.debug("<---End batchCreate()");
        return result;
    }

    public <T> List<String> batchCreates(List<String>  colNames, List<T> datas)
            throws InterruptedException, ExecutionException {

        logger.debug("---->Start batchCreate()");

        List<DocumentReference> docRefs = getDocumentReferences(colNames);
        List<String> result =batchCreate(docRefs,datas);

        logger.debug("<---End batchCreate()");
        return result;
    }


    /*
     * Write multiple objects in a batch synchronously
     */
    private  <T> List<String> batchCreate(
            List<DocumentReference> docRefs,
            List<T> datas) throws InterruptedException, ExecutionException {

        // Batch Create
        FirestoreBatchOps<T> ops = new FirestoreBatchOps<T>(
                FirestoreBatchOps.Operation.CREATE, false);
        List<String> result = ops.process(db, docRefs, datas);

        return result;
    }

    /*
     * Create multiple objects in a batch
     */
    public <T> List<String> batchCreateAsync( String  colName, List<T> datas,
                                              List<String> ids) throws InterruptedException, ExecutionException {

        logger.debug("---->Start batchCreate()");

        CollectionReference colRef = db.collection(colName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        List<String> result =batchCreateAsync(docRefs,datas);

        logger.debug("<---End batchCreate()");
        return result;
    }

    /*
     * Write multiple objects in a batch synchronously
     */
    private  <T> List<String> batchCreateAsync(
            List<DocumentReference> docRefs,
            List<T> datas) throws InterruptedException, ExecutionException {

        // Batch Create
        FirestoreBatchOps<T> ops = new FirestoreBatchOps<T>(
                FirestoreBatchOps.Operation.CREATE, true);
        List<String> result = ops.process(db, docRefs, datas);

        return result;
    }

    /*
     * Write multiple objects in a batch synchronously
     */
    public <T> void batchWrite(String colName, List<T> datas,
                               List<String> ids) throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(colName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        batchWrite(docRefs,datas);
    }
    /*
     * Write multiple objects in a batch synchronously
     */
    private <T> void batchWrite( List<DocumentReference> docRefs,
                                 List<T> datas) throws InterruptedException, ExecutionException {

        // Batch Create
        FirestoreBatchOps<T> ops = new FirestoreBatchOps<T>(
                FirestoreBatchOps.Operation.SET, false);
        ops.process(db, docRefs, datas);
    }

    /*
     * Update multiple objects in a batch syncronously
     */
    public  <T>void batchWriteAysnc( String collName, List<Map<String,Object>> datas,
                                     List<String> ids) throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(collName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        batchWriteAysnc(docRefs,datas);
    }

    /*
     * Write multiple objects in a batch synchronously
     */
    private <T>void batchWriteAysnc( List<DocumentReference> docRefs,
                                     List<T> datas) throws InterruptedException, ExecutionException {

        // Batch Create
        FirestoreBatchOps<T> ops = new FirestoreBatchOps<T>(
                FirestoreBatchOps.Operation.SET, true);
        ops.process(db, docRefs, datas);
    }

    /*
     * Update multiple objects in a batch syncronously
     */
    public <T> void batchMerge( String collName, List<Map<String,Object>> datas,
                                List<String> ids) throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(collName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        batchMerge(docRefs,datas);
    }
    /*
     * Update multiple objects in a batch syncronously
     */
    private <T> void batchMerge( List<DocumentReference> docRefs,
                                 List<Map<String,Object>> datas) throws InterruptedException, ExecutionException {

        FirestoreBatchOpsMap  ops = new FirestoreBatchOpsMap(
                FirestoreBatchOps.Operation.MERGE, false);
        ops.process(db, docRefs, datas);
    }

    /*
     * Update multiple objects in a batch syncronously
     */
    public <T> void batchMergeAsync(String collName, List<Map<String,Object>> datas,
                                    List<String> ids) throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(collName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        batchMergeAsync(docRefs,datas);
    }
    /*
     * Update multiple objects in a batch asyncronously
     */
    private void batchMergeAsync( List<DocumentReference> docRefs,
                                  List<Map<String,Object>> datas) throws InterruptedException, ExecutionException {

        FirestoreBatchOpsMap  ops = new FirestoreBatchOpsMap(
                FirestoreBatchOps.Operation.MERGE, true);
        ops.process(db, docRefs, datas);
    }

    /*
     * Update multiple objects in a batch syncronously
     */
    public <T> void batchUpdate( String collName, List<Map<String,Object>> datas,
                                 List<String> ids) throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(collName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        batchUpdate(docRefs,datas);
    }
    /*
     * Update multiple objects in a batch syncronously
     */
    private <T> void batchUpdate( List<DocumentReference> docRefs,
                                  List<Map<String,Object>> datas) throws InterruptedException, ExecutionException {

        FirestoreBatchOpsMap  ops = new FirestoreBatchOpsMap(
                FirestoreBatchOps.Operation.UPDATE, false);
        ops.process(db, docRefs, datas);
    }

    /*
     * Update multiple objects in a batch asyncronously
     */
    public void batchUpdateAsync( String collName, List<Map<String,Object>> datas,
                                  List<String> ids) throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(collName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        batchUpdateAsync(docRefs,datas);
    }

    /*
     * Update multiple objects in a batch asyncronously
     */
    private void batchUpdateAsync( List<DocumentReference> docRefs,
                                   List<Map<String,Object>> datas) throws InterruptedException, ExecutionException {

        FirestoreBatchOpsMap  ops = new FirestoreBatchOpsMap(
                FirestoreBatchOps.Operation.UPDATE, true);
        ops.process(db, docRefs, datas);
    }

    /*
     * Delete multiple objects in a batch syncronously
     */
    public <T> void batchDelete( String collName, List<String> ids)
            throws InterruptedException, ExecutionException {

        CollectionReference colRef = db.collection(collName);
        List<DocumentReference> docRefs = getDocumentReferences(colRef,ids);
        batchDelete(docRefs);
    }

    /*
     * Delete multiple objects in a batch syncronously
     */
    private <T> void batchDelete( List<DocumentReference> docRefs)
            throws InterruptedException, ExecutionException {

        FirestoreBatchOps<T>  ops = new FirestoreBatchOps<T>(
                FirestoreBatchOps.Operation.DELETE, false);
        ops.process(db, docRefs, null);
    }

    /*
     * Delete multiple objects in a batch asyncronously
     */
    public  <T>void batchDeleteAsync( List<DocumentReference> docRefs)
            throws InterruptedException, ExecutionException {

        FirestoreBatchOps<T>  ops = new FirestoreBatchOps<T>(
                FirestoreBatchOps.Operation.DELETE, false);
        ops.process(db, docRefs, null);
    }
}
