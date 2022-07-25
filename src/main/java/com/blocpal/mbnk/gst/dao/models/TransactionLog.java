package com.blocpal.mbnk.gst.dao.models;

import com.google.cloud.Timestamp;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransactionLog {

	private String txnId;

	private String a;
	
	private String t;
	
	private Timestamp ts;

	private CustomerLog cus;
	
	private ServiceProviderData sp;
	
	private String s;
	
	private UserLog u;
	
	private Timestamp uts;
}
