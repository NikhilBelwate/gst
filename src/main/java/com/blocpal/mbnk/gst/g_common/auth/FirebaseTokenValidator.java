package com.blocpal.mbnk.gst.g_common.auth;

import com.blocpal.mbnk.gst.g_common.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.database.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.validator.TokenValidator;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public class FirebaseTokenValidator implements TokenValidator {

	@Inject
	FirebaseService firebaseService;

	public Publisher<Authentication> validateToken(String token) {
		try {

			System.out.println("Validate Token");
			FirebaseToken firebaseToken=FirebaseAuth.getInstance().verifyIdToken(token);			
			return Flowable.just(new FirebaseAuthentication(firebaseToken));
		} catch (FirebaseAuthException ex) {
			ex.printStackTrace();
			return Flowable.empty();
		}
	}
	
	@Override
	public Publisher<Authentication> validateToken(String token,
            @Nullable HttpRequest<?> request) {
		return validateToken(token);
	}
}