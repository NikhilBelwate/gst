package com.blocpal.mbnk.gst.model;

import lombok.Data;

@Data
public class Transaction {

    public String txnid;
    public String operatorname;
    public String canumber;
    public String amount;
    public String comm;
    public String tds;
    public String status;
    public String refid;
    public String operatorid;
    public String dateadded;
    public String refunded;
    public String refundtxnid;
    public Object daterefunded;

    public String getStatus(){
        switch (status){
            case "1":
                return "success";
            case "0":
                return "failed";
            default:
                return "pending";
        }
    }

}
