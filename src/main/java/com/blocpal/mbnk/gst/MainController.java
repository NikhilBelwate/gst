package com.blocpal.mbnk.gst;

import com.blocpal.common.response.ServiceResult;
import com.blocpal.mbnk.gst.adapters.request.GstInquiryRequest;
import com.blocpal.mbnk.gst.adapters.request.GstProcessRequest;
import com.blocpal.mbnk.gst.service.GSTPaymentService;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.inject.Inject;
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/")
public class MainController {
    @Inject
    GSTPaymentService gstPaymentService;
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/show")
    public ServiceResult<String> show(){
        return new ServiceResult<>("$$$$$$$$$$$--working--$$$$$$$$$$$$$$");
    }
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/inquiry")
    public ServiceResult<Object> getInquiry(@Body GstInquiryRequest request){
        return new ServiceResult<>(gstPaymentService.getInquiryResponse(request));
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/process")
    public ServiceResult<Object> process(@Body GstProcessRequest request){
        return new ServiceResult<>(gstPaymentService.getProcessResponse(request));
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/verification")
    public ServiceResult<Object> verification(@QueryValue String referenceNumber){
        return new ServiceResult<>(gstPaymentService.doVerification(referenceNumber));
    }
}
