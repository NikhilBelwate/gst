package com.blocpal.mbnk.gst.dao.service;

import com.google.cloud.firestore.QueryDocumentSnapshot;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * The Interface GenericDaoService.
 * 
 * @author Shubhanshu
 *
 * @param <T> the generic type
 */
public interface GenericDaoService<T> {

	/**
	 * Creates the.
	 *
	 * @param t the t
	 */
	void create(T t, String collectionName, String document);
	
	/**
	 * Creates the.
	 *
	 * @param t the t
	 * @param collectionName the collection name
	 */
	void create(T t, String collectionName);

	/**
	 * Update.
	 *
	 * @param t the t
	 * @return the merchant
	 * @throws InterruptedException the interrupted exception
	 * @throws ExecutionException the execution exception
	 */
	T update(T t,String collectionName, String docName) throws InterruptedException, ExecutionException;
	
	
	/**
	 * Find.
	 *
	 * @param collection the collection
	 * @param field the field
	 * @return the t
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	List<QueryDocumentSnapshot> find(String collection, String field, String value) throws InterruptedException, ExecutionException;

	String add( T data,String  collName)
			throws InterruptedException, ExecutionException;

}
