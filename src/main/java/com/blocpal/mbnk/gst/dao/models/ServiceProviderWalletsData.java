package com.blocpal.mbnk.gst.dao.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceProviderWalletsData {
	
	private String name;
	
	private String walletId;
	
	private String docId;
	
	private String spsId;

	private String accessToken;

}
