package com.blocpal.mbnk.gst.dao.service.impl;


import com.blocpal.common.service.FirestoreService;
import com.blocpal.common.utility.FirestoreUtility;
import com.blocpal.common.utility.QueryBuilder;
import com.blocpal.mbnk.gst.dao.models.SPSData;
import com.blocpal.mbnk.gst.dao.models.ServiceProviderWalletsData;
import com.blocpal.mbnk.gst.dao.service.ServiceProviderDataDaoService;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Singleton
public class ServiceProviderDataDaoServiceImpl extends GenericDaoServiceImpl<SPSData> implements ServiceProviderDataDaoService {

	@Inject
	private FirestoreService dbSrvc;
	private Firestore db = null;
	private FirestoreUtility dbUtility = null;

	@PostConstruct
	public void initialize() throws IOException {
		db = dbSrvc.getDb();
		dbUtility = new FirestoreUtility(db);
	}

	@Override
	public ServiceProviderWalletsData getServiceProvider(String name) {

//		Query querySps = fireBaseConfig.getObject().collection("sps").whereEqualTo("n", name);
		QueryBuilder query = QueryBuilder.create(db, "sps").whereEqualTo("n", name);
		ServiceProviderWalletsData data = null;
		try {
			for (DocumentSnapshot documents : query.execute()) {
				Map<String,Object> wltsMap = (Map<String, Object>) documents.get("wlts");
				String walletId = String.valueOf(wltsMap.get("LIC"));
				data = ServiceProviderWalletsData.builder()
						.docId(documents.getId())
						.name(name)
						.walletId(walletId)
						.spsId(documents.getId())
						//.accessToken(token)
						.build();
			}
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
		return data;
	}

	@Override
	public Map<String, SPSData> getServiceProviderData(String name) throws ExecutionException, InterruptedException {
		Map<String,SPSData> dataMap = new HashMap<>();
		System.out.println("getServiceProviderData called :::: ");
		List<QueryDocumentSnapshot> documents = find("sps","n",name);
		System.out.println("documents :::: "+documents);
		for (QueryDocumentSnapshot document : documents) {
			System.out.println("document id :::::::::: "+document.getId());
			dataMap.put(document.getId(),document.toObject(SPSData.class));
		}
		return dataMap;
	}

}
