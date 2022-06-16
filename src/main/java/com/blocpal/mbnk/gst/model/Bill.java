package com.blocpal.mbnk.gst.model;

import lombok.Data;

@Data
public class Bill {
    private String billAmount;
    private String billnetamount;
    private String dueDate;
    private String maxBillAmount;
    private Boolean acceptPayment;
    private Boolean acceptPartPay;
    private String cellNumber;
    private String userName;
}
