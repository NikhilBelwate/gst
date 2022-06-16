package com.blocpal.mbnk.gst.adapters.provider.paysprint;

import com.blocpal.mbnk.gst.adapters.response.FetchConsumerResponse;
import com.blocpal.mbnk.gst.adapters.response.StatusResponse;
import com.blocpal.mbnk.gst.exception.FastTagException;
import com.blocpal.mbnk.gst.g_common.HTTPRequestMethod;
import com.blocpal.mbnk.gst.adapters.response.OperatorListResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class PaysprintService {
    @Inject
    PaysprintClient fastTagClient;

    @Inject
    PaysprintAuthService authService;
    FastTagException exception;

    public FastTagException getException() {
        return exception;
    }

    private void setException(FastTagException exception) {
        this.exception = exception;
    }

    Gson gson;
    @PostConstruct
    public void initialize() {
        gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }

/*
    public OperatorListResponse getOperatorsList(){
        try{
            OperatorListResponse response= (OperatorListResponse) fastTagClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.operatorListURI,"",authService.getHeaders(),OperatorListResponse.class);
            return response;
        }catch (FastTagException fastTagException){
            setException(fastTagException);
            return null;
        }
    }

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
