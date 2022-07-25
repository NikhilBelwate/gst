package com.blocpal.mbnk.gst.adapters.request;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Introspected
public class ProcessRequest {
    @NotNull
    private String gibTxnId;
    @NotNull
    private String cpin;
    @NotNull(message = "amount is required")
    private String amount;
    @NotNull
    private String reference_number;
    @NotNull
    private String retailerWalletId;
    @NotNull
    private String retailerId;
    @NotNull
    private String txnId;
}
