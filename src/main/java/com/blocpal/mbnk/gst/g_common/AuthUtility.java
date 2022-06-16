package com.blocpal.mbnk.gst.g_common;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

public class AuthUtility {
    private static final Logger logger = LoggerFactory.getLogger(AuthUtility.class);
    private static final String BEARER = "Bearer";
    private static final String BASIC = "Basic";

    public static String verifyToken (String token) {

        String [] split= token.split("\\s+");
        if (split.length != 2)
            throw new AuthHeaderFormatException();

        String bearer = split[0];
        String bearerToken = split[1];
        if (!bearer.equalsIgnoreCase(BEARER))
            throw new AuthHeaderFormatException();

        FirebaseToken result;
        try {
            result = FirebaseAuth.getInstance().verifyIdToken(bearerToken);
        } catch (FirebaseAuthException e) {
            throw new AuthFailedException(e.getMessage());
        }

        String uuid = result.getUid();
        logger.debug("extracted uuid:"+uuid);

        return uuid;
    }

    /*
     * Get user id from authorization header
     */
    public static String getId (String authHeaderValue) {
        String id = null;
        String [] split= authHeaderValue.split("\\s+");
        if (split.length == 2) {
            String authType = split[0];
            String authToken = split[1];

            if (authType.equalsIgnoreCase(BEARER)) {
                id = getUserFromFirebase(authToken);
            }else if (authType.equalsIgnoreCase(BASIC)) {
                id = getUser(authToken);
            }
        }

        return id;
    }

    /*
     * Encode name:password in base64
     */
    public static String encodeBase64(String name, String password) {

        String basicCreds = name+":"+password;
        Base64.Encoder encoder = Base64.getEncoder();
        String base64Creds = new String(encoder.encode((basicCreds.getBytes())));
        return base64Creds;
    }

    /*
     * Get username from Basic token
     */
    private static String getUser (String basicToken) {

        String user = null;
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] decodedBytes = decoder.decode(basicToken);
            String decodedValue = new String(decodedBytes);

            String [] split= decodedValue.split(":");
            if (split.length == 2) {
                user = split[0];
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /*
     * Get User Id from Firebase.
     */
    private static String getUserFromFirebase (String bearerToken) {

        String uuid = null;
        FirebaseToken result;
        try {
            result = FirebaseAuth.getInstance().verifyIdToken(bearerToken);
            uuid = result.getEmail() == null ?result.getUid():result.getEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uuid;
    }
}
