package com.blocpal.mbnk.gst.g_common;

import com.blocpal.mbnk.gst.g_common.model.Pair;
import com.blocpal.mbnk.gst.g_common.model.Sort;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.Query.Direction;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class QueryBuilder {
	
	private Firestore db;
	
	private String collection;

	
	private Integer pageSize;
	private String lastDocId;
	private Pair<String,Object> contains = null;
	private Pair<String,List<? extends Object>> inCond = null;
	private List<Pair<String,Direction>> orderBys = new ArrayList<Pair<String,Direction>>();
	private List<Pair<String,Object>> equalTos = new ArrayList<Pair<String,Object>>();
	private List<Pair<String,Object>> greaterThanEqualTos = new ArrayList<Pair<String,Object>>();
	private List<Pair<String,Object>> lessThans = new ArrayList<Pair<String,Object>>();
	
	private QueryBuilder(Firestore db, String collName){
		this.db = db;
		this.collection = collName;
	}
		
	public static   QueryBuilder  create(Firestore db, String collName) {
		return new QueryBuilder(db,collName);		
	}
	
	public QueryBuilder withCollection (@NotNull String collName) {
		this.collection = collName;
		return this;
	}
	
	public QueryBuilder orderBy (@NotNull String field) {
		Direction dir = Direction.ASCENDING;		
		Pair<String,Direction> query = Pair.of(field, dir);
		orderBys.add(query);
		return this;
	}
	
	public QueryBuilder orderBy (@NotNull String field,@NotNull Sort sort) {
		Direction dir = getDirection(sort);
		
		Pair<String,Direction> query = Pair.of(field, dir);
		orderBys.add(query);
		return this;
	}
	
	public QueryBuilder whereArrayContains (@NotNull String field, 
			Object value) {
		
		if (null != value) {
			contains = Pair.of(field, value);
		}		
		return this;
	}
	public QueryBuilder whereEqualTo(@NotNull String field, 
			 Object value){
		
		if (null != value) {
			Pair<String,Object> query = Pair.of(field, value);
			equalTos.add(query);
		}
		return this;
	}
	
	public QueryBuilder whereIn(@NotNull String field, 
			 List<? extends Object> value){
		
		if (null != value) {
			inCond = Pair.of(field, value);			
		}
		return this;
	}
	
	public QueryBuilder whereGreaterThanOrEqualTo(@NotNull String field, 
			Object value) {
		
		if (null != value) {
			Pair<String,Object> query = Pair.of(field, value);		
			greaterThanEqualTos.add(query);
		}
		return this;		
	}
	
	public QueryBuilder whereLessThan(@NotNull String field, 
			Object value) {
		
		if (null != value) {
			Pair<String,Object> query = Pair.of(field, value);		
			lessThans.add(query);
		}
		return this;		
	}
	
	public QueryBuilder limit(@NotNull Integer pageSize) {
		
		this.pageSize=pageSize;
		return this;
	}
	
	public QueryBuilder withPagination(@NotNull Integer pageSize, 
			String lastDocId) {
		
		this.pageSize=pageSize;
		this.lastDocId = lastDocId;
		
		return this;
	}
	
	public <T> List<T> execute ( Class<? extends T> type) throws InterruptedException, ExecutionException{
		
		List <T> result = new ArrayList<T>();
		List<QueryDocumentSnapshot> docSnapshots = execute();
		for (QueryDocumentSnapshot queryDocSnapshot: docSnapshots){
			T record = queryDocSnapshot.toObject(type);
			result.add(record);
		} 
		return result;
	}
	
	public List<QueryDocumentSnapshot> execute () throws InterruptedException, ExecutionException {
		
		CollectionReference collRef = db.collection(collection);
		
		Query query = collRef;
		for (Pair<String,Direction> pair:orderBys) {
			query = query.orderBy(pair.first,pair.second);
		}		
		if (null != contains) {
			query =query.whereArrayContains(contains.first, contains.second);
		}
		if ( null != inCond) {
			query =query.whereIn(inCond.first, inCond.second);
		}
	
		for (Pair<String,Object> pair :equalTos) {
			query =query.whereEqualTo(pair.first, pair.second);
		}		
		for (Pair<String,Object> pair :lessThans) {
			query =query.whereLessThan(pair.first, pair.second);
		}		
		for (Pair<String,Object> pair: greaterThanEqualTos) {
			query =query.whereGreaterThanOrEqualTo(pair.first, pair.second);
		}
		
		if (lastDocId != null) {
			DocumentReference docRef = collRef.document(lastDocId);
			DocumentSnapshot snapshot = docRef.get().get();	    		
			query = query.startAfter(snapshot);
		}		
		if (pageSize != null) {
			query = query.limit(pageSize);				
		}
		
		ApiFuture<QuerySnapshot> futureQuerySnapshot = query.get();
		QuerySnapshot querySnapshot = futureQuerySnapshot.get();
		List<QueryDocumentSnapshot> docSnapshots = querySnapshot.getDocuments();
		
		return docSnapshots;
	}
	
	private Direction getDirection (Sort sort) {
		Direction result = Direction.ASCENDING;
		if (null != sort && sort.equals(Sort.DESCENDING))
			result = Direction.DESCENDING;
		return result;
	}
}
