package com.blocpal.mbnk.gst.request;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@Introspected
public class GSTProcessRequest {
    @NotNull
    private String gibTxnId;
    @NotNull
    private String cpin;
    @NotNull(message = "amount is required")
    @Size(min = 1,message = "Invalid Amount")
    private String amount;
    @NotNull
    private String reference_number;
    @NotNull
    private String retailerWalletId;
}
