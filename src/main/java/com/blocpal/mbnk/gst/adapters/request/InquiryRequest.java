package com.blocpal.mbnk.gst.adapters.request;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Introspected
public class InquiryRequest {
    @NotNull
    private String gstin;
    @NotEmpty
    private String cpin;
    @NotNull
    private String amount;
    @NotEmpty
    private String retailerWalletId;
}
