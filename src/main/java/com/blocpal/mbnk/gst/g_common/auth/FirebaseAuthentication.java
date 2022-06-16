package com.blocpal.mbnk.gst.g_common.auth;

import com.google.firebase.auth.FirebaseToken;
import io.micronaut.security.authentication.Authentication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FirebaseAuthentication implements Authentication {

	private static final long serialVersionUID = 7479336418057292308L;
	
	private FirebaseToken firebaseToken;

	public FirebaseAuthentication(FirebaseToken firebaseToken) {
		this.firebaseToken = firebaseToken;
	}

	@Override
	public Map<String, Object> getAttributes() {

		Map<String, Object> map = null;
		if (null == firebaseToken)
			map = new HashMap<String, Object>();
		else
			map = firebaseToken.getClaims();

		return Collections.unmodifiableMap(map);
	}

	@Override
	public String getName() {
		
		String name = "default";
		if (null != firebaseToken) {
			name = firebaseToken.getUid();
		}
		return name;
	}
}
