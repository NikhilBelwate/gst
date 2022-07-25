package com.blocpal.mbnk.gst;

import com.blocpal.common.response.ServiceResult;
import com.blocpal.mbnk.gst.adapters.request.InquiryRequest;
import com.blocpal.mbnk.gst.adapters.request.ProcessRequest;
import com.blocpal.mbnk.gst.adapters.response.ProcessResponse;
import com.blocpal.mbnk.gst.exception.GstException;
import com.blocpal.mbnk.gst.response.GstInquiryResponse;
import com.blocpal.mbnk.gst.service.GSTPaymentService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.security.Principal;
@Controller("/")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class MainController {
    @Inject
    GSTPaymentService gstPaymentService;
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/show")
    public ServiceResult<String> show(){
        return new ServiceResult<>("$$$$$$$$$$$--working--$$$$$$$$$$$$$$");
    }
    //@Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/inquiry")
    public ServiceResult<GstInquiryResponse> getInquiry(Principal principal,@Header("Authorization") String token, @Valid @Body InquiryRequest request) throws GstException {
        String retailerId=principal.getName();
        if(retailerId==null)
            retailerId="eWW330PFpFyjeBe4pJt2";
        return new ServiceResult<>(gstPaymentService.getInquiryResponse(request,retailerId,true));
    }
    //@Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/refetch")
    public ServiceResult<GstInquiryResponse> refetch(Principal principal,@Header("Authorization") String token, @Valid @Body InquiryRequest request) throws GstException {
        String retailerId=principal.getName();
        if(retailerId==null)
            retailerId="eWW330PFpFyjeBe4pJt2";
        return new ServiceResult<>(gstPaymentService.getInquiryResponse(request, retailerId, false));
    }

    //@Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/process")
    public ServiceResult<ProcessResponse> process(Principal principal, @Header("Authorization") String token, @Valid @Body ProcessRequest request) throws GstException {
        String retailerId=principal.getName();
        if(retailerId==null)
            retailerId="eWW330PFpFyjeBe4pJt2";
        return new ServiceResult<>(gstPaymentService.getProcessResponse(request,retailerId));
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/verification")
    public ServiceResult<Object> verification(@Header("Authorization") String token, @NotEmpty @QueryValue String referenceNumber) throws GstException {
        return new ServiceResult<>(gstPaymentService.doVerification(referenceNumber));
    }
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/updatePendingTxnStatus")
    public ServiceResult<Object> updatePendingTxnStatus() {
        String result = "{'Total_updated':'"+gstPaymentService.doStatusUpdate()+"'}";
        return new ServiceResult<>(result);
    }
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Produces(MediaType.APPLICATION_PDF)
    @Get("/download")
    public byte[] download(@Header("Authorization") String token, @NotEmpty @QueryValue String referenceNumber){
        return gstPaymentService.download(referenceNumber).getData().getDecodedPdfBytes().getBytes();
    }
}
