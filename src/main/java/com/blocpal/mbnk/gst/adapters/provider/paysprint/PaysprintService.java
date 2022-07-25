package com.blocpal.mbnk.gst.adapters.provider.paysprint;

import com.blocpal.common.client.HTTPRequestMethod;
import com.blocpal.mbnk.gst.adapters.request.DownloadRequest;
import com.blocpal.mbnk.gst.adapters.request.InquiryRequest;
import com.blocpal.mbnk.gst.adapters.request.ProcessRequest;
import com.blocpal.mbnk.gst.adapters.request.VerificationRequest;
import com.blocpal.mbnk.gst.adapters.response.DownloadResponse;
import com.blocpal.mbnk.gst.adapters.response.InquiryResponse;
import com.blocpal.mbnk.gst.adapters.response.ProcessResponse;
import com.blocpal.mbnk.gst.adapters.response.VerificationResponse;
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


    Gson gson;
    @PostConstruct
    public void initialize() {
        gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }
    
    public InquiryResponse getGstInquiryResponse(InquiryRequest request, Boolean isNew) throws GstException {
        try{
            String url;
            if(isNew)
                url=PaysprintEndpoints.inquiryURL;
            else
                url=PaysprintEndpoints.refetchURL;

            InquiryResponse response= (InquiryResponse) paysprintClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    url,gson.toJson(request),authService.getHeaders(), InquiryResponse.class);
            return response;
        }catch (GstException gstException){
            throw gstException;
        }
    }
    public DownloadResponse getGstDownloadResponse(String referenceNumber) throws GstException {
        try{
            DownloadRequest request=new DownloadRequest();
            request.setReferenceNumber(referenceNumber);
            DownloadResponse response= (DownloadResponse) paysprintClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.downloadURL,gson.toJson(request),authService.getHeaders(), DownloadResponse.class);
            return response;
        }catch (GstException gstException){
            throw gstException;
        }
    }
    public ProcessResponse getGstProcessResponse(ProcessRequest request) throws GstException {
        try{
            ProcessResponse response= (ProcessResponse) paysprintClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.processURL,gson.toJson(request),authService.getHeaders(), ProcessResponse.class);
            return response;
        }catch (GstException gstException){
            throw gstException;
        }
    }
    public VerificationResponse getGstVerificationResponse(VerificationRequest request) throws GstException {
        try{
            VerificationResponse response= (VerificationResponse) paysprintClient.invokeJsonRequest(HTTPRequestMethod.POST,
                    PaysprintEndpoints.verificationURL,gson.toJson(request),authService.getHeaders(), VerificationResponse.class);
            return response;
        }catch (GstException gstException){
            throw gstException;
        }
    }
}
