package com.blocpal.mbnk.gst.g_common;

public class CommonAppStatusCodes {
    // Unknown Failur
    static public final Integer UNKNOWN = -1;

    // Success
    static public final Integer  SUCCESSFUL = 0000;

    //General Exception [1301-1340]
    static public final Integer DATE_FORMAT_EXCEPTION = 1301;
    static public final Integer FILE_HEADER_EXCEPTION = 1302;
    static public final Integer FIRESTORE_EXCEPTION = 1303;
    static public final Integer TRANSACTION_EXCEPTION = 1304;
    static public final Integer OBJECT_MAPPER_EXCEPTION = 1305;
    static public final Integer API_CALL_EXCEPTION = 1306;
    static public final Integer OTP_EXPIRED_EXCEPTION = 1307;
    static public final Integer OTP_INVALID_EXCEPTION = 1308;
    static public final Integer OTP_NOT_FOUND_EXCEPTION = 1309;

    // Authentication Exception [1401-1410]
    static public final Integer AUTH_HEADER_FORMAT_EXCEPTION = 1401;
    static public final Integer AUTH_FAILED_EXCEPTION = 1402;
}
