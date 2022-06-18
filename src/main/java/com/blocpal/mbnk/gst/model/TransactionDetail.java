package com.blocpal.mbnk.gst.model;

import lombok.Data;

@Data
public class TransactionDetail {
    public String igstAccCode;
    public String gstin;
    public String gibTxnId;
    public String cessAccCode;
    public String cessAmt;
    public String beneficiaryStateCd;
    public String sgstAmt;
    public String sgstAccCode;
    public String merchantid;
    public String chlnExpDt;
    public String cpin;
    public String igstAmt;
    public String cgstAmt;
    public String totalAmt;
    public String cgstAccCode;
}
