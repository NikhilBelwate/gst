package com.blocpal.mbnk.gst.dao.service;


import com.blocpal.mbnk.gst.dao.models.SPSData;
import com.blocpal.mbnk.gst.dao.models.ServiceProviderWalletsData;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ServiceProviderDataDaoService extends GenericDaoService<SPSData> {
	
	 ServiceProviderWalletsData getServiceProvider(String name);

	Map<String, SPSData> getServiceProviderData(String name) throws ExecutionException, InterruptedException;



}
