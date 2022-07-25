package com.blocpal.mbnk.gst.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Introspected
public class TransactionDetail {
    @NotEmpty
    public String igstAccCode;
    @NotBlank
    public String gstin;
    @NotEmpty
    public String gibTxnId;
    @NotEmpty
    public String cessAccCode;
    @NotEmpty
    public String cessAmt;
    @NotBlank
    public String beneficiaryStateCd;
    @NotEmpty
    public String sgstAmt;
    @NotBlank
    public String sgstAccCode;
    @NotBlank
    public String merchantid;
    @NotBlank
    public String chlnExpDt;
    @NotBlank
    public String cpin;
    @NotEmpty
    public String igstAmt;
    @NotEmpty
    public String cgstAmt;
    @NotEmpty
    public String totalAmt;
    @NotEmpty
    public String cgstAccCode;
}
