package com.blocpal.mbnk.gst.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ProcessDetails {
    @SerializedName("TxnDate")
    @Expose
    public String txnDate;
    @SerializedName("cpin")
    @Expose
    public String cpin;
    @SerializedName("TransactionStatus")
    @Expose
    public String transactionStatus;
    @SerializedName("apiName")
    @Expose
    public String apiName;
    @SerializedName("StatusDesc")
    @Expose
    public String statusDesc;
    @SerializedName("gstin")
    @Expose
    public String gstin;
    @SerializedName("gibTxnId")
    @Expose
    public String gibTxnId;
    @SerializedName("total_amt")
    @Expose
    public String totalAmt;
    @SerializedName("cin")
    @Expose
    public String cin;
    @SerializedName("merchantid")
    @Expose
    public String merchantid;
    @SerializedName("chln_exp_dt")
    @Expose
    public String chlnExpDt;
}
