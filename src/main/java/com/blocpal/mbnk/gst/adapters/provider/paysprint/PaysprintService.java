package com.blocpal.mbnk.gst.adapters.provider.paysprint;

import com.blocpal.common.client.HTTPRequestMethod;
import com.blocpal.mbnk.gst.adapters.request.GstInquiryRequest;
import com.blocpal.mbnk.gst.adapters.request.GstProcessRequest;
import com.blocpal.mbnk.gst.adapters.request.GstVerificationRequest;
import com.blocpal.mbnk.gst.adapters.response.GstInquiryResponse;
import com.blocpal.mbnk.gst.adapters.response.GstProcessResponse;
import com.blocpal.mbnk.gst.adapters.response.GstVerificationResponse;
import com.blocpal.mbnk.gst.exception.GstException;
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
    public GstProcessResponse getGstProcessResponse(GstProcessRequest request){
        try{
            GstProcessResponse response= (GstProcessResponse) paysprintClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.processURL,gson.toJson(request),authService.getHeaders(),GstProcessResponse.class);
            return response;
        }catch (Exception exception){
            this.exception=new GstException(exception);
            return null;
        }
    }
    public GstVerificationResponse getGstVerificationResponse(GstVerificationRequest request){
        try{
            GstVerificationResponse response= (GstVerificationResponse) paysprintClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.verificationURL,gson.toJson(request),authService.getHeaders(),GstVerificationResponse.class);
            return response;
        }catch (Exception exception){
            this.exception=new GstException(exception);
            return null;
        }
    }
/*
    public FetchConsumerResponse getConsumerResponse(FetchConsumerRequest fetchConsumerRequest){
        try{
            FetchConsumerResponse response= (FetchConsumerResponse) gstClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.fetchConsumerDetailsURI,gson.toJson(fetchConsumerRequest),authService.getHeaders(),FetchConsumerResponse.class);
            return response;

        }catch (gstException gstException){
            setException(gstException);
            return null;
        }
    }


    public StatusResponse getStatusResponse(Integer referenceid) {
        try{
            Map<String,Integer> requestData=new HashMap<>();
            requestData.put("referenceid",referenceid);
            StatusResponse response= (StatusResponse) gstClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.statusURI,gson.toJson(requestData),authService.getHeaders(),StatusResponse.class);
            return response;

        }catch (gstException gstException){
            setException(gstException);
            return null;
        }
    }*/
}
