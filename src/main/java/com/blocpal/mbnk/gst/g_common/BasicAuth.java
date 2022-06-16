package com.blocpal.mbnk.gst.g_common;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BasicAuth {
	
	private String userName;
	
	private String passWord;

}
