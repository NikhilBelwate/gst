package com.blocpal.mbnk.gst.adapters.provider.paysprint;

public interface PaysprintEndpoints {
    String baseLocalURL = "http://localhost:8081";
    String baseUATURL = "https://paysprint.in/service-api/api/v1/service/gst/tax/";
    String basePRODURL = "https://paysprint.in/service-api/api/v1/service/gst/tax/";

    String inquiryURL="inquiry";

    String processURL="process";
    String downloadURL="download";

    String verificationURL="verification";
    String SUCCESS = "SUCCESS";
    String FAILED="FAILED";
    String PENDING="PENDING";
    String SERVICE_NAME = "paysprint";
    String STATUS = "s";
    String TXNS = "txns";
    String Type="GST-CHALLAN";
    String SubType="TAX-PAY";
    String refetchURL = "refetch";

    interface walletConstant{
        String FAILURE = "F";
        String SUCCESS = "S";
        String FAILED="F";
        String PENDING="P";
    }
}
