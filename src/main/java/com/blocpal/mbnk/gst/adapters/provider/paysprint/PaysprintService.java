package com.blocpal.mbnk.gst.adapters.provider.paysprint;

import com.blocpal.mbnk.gst.adapters.request.GstInquiryRequest;
import com.blocpal.mbnk.gst.adapters.response.GstInquiryResponse;
import com.blocpal.mbnk.gst.exception.GstException;
import com.blocpal.mbnk.gst.g_common.HTTPRequestMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class PaysprintService {
    @Inject
    PaysprintClient paysprintClient;

    @Inject
    PaysprintAuthService authService;
    GstException exception;

    public GstException getException() {
        return exception;
    }

    private void setException(GstException exception) {
        this.exception = exception;
    }

    Gson gson;
    @PostConstruct
    public void initialize() {
        gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }


    public GstInquiryResponse getGstInquiryResponse(GstInquiryRequest request){
        try{
            GstInquiryResponse response= (GstInquiryResponse) paysprintClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.inquiryURL,gson.toJson(request),authService.getHeaders(),GstInquiryResponse.class);
            return response;
        }catch (Exception exception){
            this.exception=new GstException(exception);
            return null;
        }
    }
/*
    public FetchConsumerResponse getConsumerResponse(FetchConsumerRequest fetchConsumerRequest){
        try{
            FetchConsumerResponse response= (FetchConsumerResponse) fastTagClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.fetchConsumerDetailsURI,gson.toJson(fetchConsumerRequest),authService.getHeaders(),FetchConsumerResponse.class);
            return response;

        }catch (FastTagException fastTagException){
            setException(fastTagException);
            return null;
        }
    }


    public StatusResponse getStatusResponse(Integer referenceid) {
        try{
            Map<String,Integer> requestData=new HashMap<>();
            requestData.put("referenceid",referenceid);
            StatusResponse response= (StatusResponse) fastTagClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.statusURI,gson.toJson(requestData),authService.getHeaders(),StatusResponse.class);
            return response;

        }catch (FastTagException fastTagException){
            setException(fastTagException);
            return null;
        }
    }*/
}
