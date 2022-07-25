package com.blocpal.mbnk.gst.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Introspected
public class ProcessDetails {
    @SerializedName("TxnDate")
    @Expose
    @NotNull
    public String txnDate;
    @SerializedName("cpin")
    @Expose
    @NotEmpty
    public String cpin;
    @SerializedName("TransactionStatus")
    @Expose
    public String transactionStatus;
    @SerializedName("apiName")
    @Expose
    @NotNull
    public String apiName;
    @SerializedName("StatusDesc")
    @Expose
    public String statusDesc;
    @SerializedName("gstin")
    @Expose
    @NotEmpty
    public String gstin;
    @SerializedName("gibTxnId")
    @Expose
    public String gibTxnId;
    @SerializedName("total_amt")
    @Expose
    @NotEmpty
    public String totalAmt;
    @SerializedName("cin")
    @Expose
    @NotEmpty
    public String cin;
    @SerializedName("merchantid")
    @Expose
    public String merchantid;
    @SerializedName("chln_exp_dt")
    @Expose
    public String chlnExpDt;
}
