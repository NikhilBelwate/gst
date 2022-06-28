package com.blocpal.mbnk.gst.service;

import com.blocpal.common.response.ServiceResult;
import com.blocpal.common.response.ServiceStatus;
import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintService;
import com.blocpal.mbnk.gst.adapters.request.GstInquiryRequest;
import com.blocpal.mbnk.gst.adapters.request.GstProcessRequest;
import com.blocpal.mbnk.gst.adapters.request.GstVerificationRequest;
import com.blocpal.mbnk.gst.adapters.response.GstInquiryResponse;
import com.blocpal.mbnk.gst.adapters.response.GstProcessResponse;
import com.blocpal.mbnk.gst.adapters.response.GstVerificationResponse;
import io.micronaut.context.annotation.Bean;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Bean
public class GSTPaymentService {
    @Inject
    private PaysprintService paysprintService;

    private ServiceStatus serviceStatus;
    public ServiceResult<Object> getInquiryResponse(GstInquiryRequest request){
        GstInquiryResponse response=paysprintService.getGstInquiryResponse(request);
        if(response!=null){
            return new ServiceResult<>(response);
        }else{
            List<String> errors= Arrays.stream(paysprintService.getException().getStackTrace()).map(e -> e.toString() ).collect( toList() );
            serviceStatus=new ServiceStatus(5001,paysprintService.getException().getMessage(),errors);
            return new ServiceResult<>("{}",serviceStatus);
        }
    }

    public ServiceResult<Object> getProcessResponse(GstProcessRequest request){
        GstProcessResponse response=paysprintService.getGstProcessResponse(request);
        if(response!=null){
            return new ServiceResult<>(response);
        }else{
            List<String> errors= Arrays.stream(paysprintService.getException().getStackTrace()).map(e -> e.toString() ).collect( toList() );
            serviceStatus=new ServiceStatus(5001,paysprintService.getException().getMessage(),errors);
            return new ServiceResult<>("{}",serviceStatus);
        }
    }
    public ServiceResult<Object> doVerification(String referenceNumber){
        GstVerificationRequest request=new GstVerificationRequest();
        request.setReferenceNumber(referenceNumber);
        GstVerificationResponse response=paysprintService.getGstVerificationResponse(request);
        if(response!=null){
            return new ServiceResult<>(response);
        }else{
            List<String> errors= Arrays.stream(paysprintService.getException().getStackTrace()).map(e -> e.toString() ).collect( toList() );
            serviceStatus=new ServiceStatus(5001,paysprintService.getException().getMessage(),errors);
            return new ServiceResult<>("{}",serviceStatus);
        }
    }
}
