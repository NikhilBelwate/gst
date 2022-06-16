package com.blocpal.mbnk.gst.g_common;

import java.security.SecureRandom;
import java.util.Random;

public class IdGenUtility {
    private static final Random RANDOM = new SecureRandom();

    // Firestore Document Id
    private static final int ID_LENGTH = 20;
    private static final String ID_ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Txn Display Id
    private static final int ID_CAPS_LENGTH = 15;
    private static final String ID_CAPS_ALPHABET=
            "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    // OTP
    private static final int OTP_LENGTH = 6;
    private static final String OTP_ALPHABETS = "123456789";

    // VA
    private static final int VA_LENGTH = 10;
    private static final String VA_ALPHABETS = "0123456789";


    /*
     * Get Id
     */
    public static String getId(){
        return getId(ID_LENGTH,ID_ALPHABET);
    }

    /*
     * Get Display Id
     */
    public static String getCapsId() {
        return getId(ID_CAPS_LENGTH,ID_CAPS_ALPHABET);
    }

    /*
     * Get Virtual Account Id
     */
    public static String getVAcctNo() {
        String id = "ZMBNKZ"+"22"+"5"+ getId(VA_LENGTH-1, VA_ALPHABETS);
        return id;
    }
    /*
     * get OTP
     */
    public static String getOtp() {
        return getId(OTP_LENGTH,OTP_ALPHABETS);
    }


    private static String getId (int length, String alphabets) {
        StringBuilder builder = new StringBuilder();
        int maxRandom = alphabets.length();
        for (int i = 0; i < length; i++) {
            builder.append(alphabets.charAt(RANDOM.nextInt(maxRandom)));
        }
        return builder.toString();
    }
}
