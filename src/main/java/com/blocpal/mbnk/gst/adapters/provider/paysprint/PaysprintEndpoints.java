package com.blocpal.mbnk.gst.adapters.provider.paysprint;

public interface PaysprintEndpoints {
    String baseLocalURL = "http://localhost:8080";
    String baseUATURL = "https://paysprint.in/service-api/api/v1/service/fastag/Fastag/";
    String basePRODURL = "https://paysprint.in/service-api/api/v1/service/fastag/Fastag/";

    String operatorListURI="operatorsList";
    String fetchConsumerDetailsURI="fetchConsumerDetails";
    String statusURI="status";

}
