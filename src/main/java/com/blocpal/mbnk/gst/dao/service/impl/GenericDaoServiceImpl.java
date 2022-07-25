package com.blocpal.mbnk.gst.dao.service.impl;

import com.blocpal.common.service.FirestoreService;
import com.blocpal.common.utility.FirestoreUtility;
import com.blocpal.common.utility.QueryBuilder;
import com.blocpal.mbnk.gst.dao.service.GenericDaoService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * The Class GenericDaoServiceImpl.
 * 
 * @author Shubhanshu Pal
 *
 * @param <T> the generic type
 */
@Singleton
@Slf4j
public class GenericDaoServiceImpl<T> implements GenericDaoService<T> {

	@Inject
	private FirestoreService dbSrvc;

	protected Firestore db = null;
	protected FirestoreUtility dbUtility = null;

	@PostConstruct
	public void initialize() throws IOException {
		db = dbSrvc.getDb();
		dbUtility = new FirestoreUtility(db);
	}
	/**
	 * Creates the.
	 *
	 * @param t the t
	 * @param collectionName the collection name
	 */
	@Override
	public void create(T t, String collectionName, String document) {
		DocumentReference docRef = dbUtility.getDocumentReference(collectionName, document);
		ApiFuture<WriteResult> result = docRef.set(t);
	}

	@Override
	public void create(T t, String collectionName) {
		DocumentReference docRef = dbUtility.getDocumentReference(collectionName);
		ApiFuture<WriteResult> result = docRef.set(t);
	}

	/**
	 * Update.
	 *
	 * @param t the t
	 * @param collectionName the collection name
	 * @return the t
	 * @throws InterruptedException the interrupted exception
	 * @throws ExecutionException the execution exception
	 */
	@Override
	public T update(T t, String collectionName, String docName) throws InterruptedException, ExecutionException {
//		fireBaseConfig.getObject().document(collectionName.concat("/").concat(docName)).set(t,
//				SetOptions.merge());
		DocumentReference docRef = dbUtility.getDocumentReference(collectionName, docName);
		docRef.set(t, SetOptions.merge());
		return t;
	}

	/**
	 * Find.
	 *
	 * @param collection the collection
	 * @param field the field
	 * @param value the value
	 * @return the list
	 * @throws InterruptedException the interrupted exception
	 * @throws ExecutionException the execution exception
	 */
	@Override
	public List<QueryDocumentSnapshot> find(String collection, String field, String value) throws InterruptedException, ExecutionException {
		return QueryBuilder.create(db, collection).whereEqualTo(field, value).execute();
	}

	@Override
	public  String add( T data,String  collName)
			throws InterruptedException, ExecutionException {
		return dbUtility.add(collName, data);
	}

}
