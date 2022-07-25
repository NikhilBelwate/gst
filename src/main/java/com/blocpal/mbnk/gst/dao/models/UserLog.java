package com.blocpal.mbnk.gst.dao.models;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLog {
	
	private String id;
	
	private String wId;

	@PropertyName("wId")
	public String getwId() {
		return wId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setwId(String wId) {
		this.wId = wId;
	}

	
	

}
