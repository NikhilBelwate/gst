package com.blocpal.mbnk.gst.model;

import lombok.Data;

import java.util.List;

@Data
public class Transaction {

    public String amount;
    public String cpin;
    public String apiName;
    public String gstin;
    public List<TransactionDetail> transactionDetails;

}
