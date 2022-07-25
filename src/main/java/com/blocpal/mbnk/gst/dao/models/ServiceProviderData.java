package com.blocpal.mbnk.gst.dao.models;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceProviderData {
	
	
	private String gibTxnId;
	
	private String id;
	
	private String rm;
	
	private String tId;
	
	private BillerData blr;
	
	private Map<String, Object> d;


	@PropertyName("tId")
	public String gettId() {
		return tId;
	}


	public String getId() {
		return id;
	}

	public String getRm() {
		return rm;
	}

	public BillerData getBlr() {
		return blr;
	}

	public Map<String, Object> getD() {
		return d;
	}


	public void setId(String id) {
		this.id = id;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public void settId(String tId) {
		this.tId = tId;
	}

	public void setBlr(BillerData blr) {
		this.blr = blr;
	}

	public void setD(Map<String, Object> d) {
		this.d = d;
	}


	@PropertyName("gibTxnId")
	public String getGibTxnId() {
		return gibTxnId;
	}

	@PropertyName("gibTxnId")
	public void setGibTxnId(String gibTxnId) {
		this.gibTxnId = gibTxnId;
	}
	
	
	

}
