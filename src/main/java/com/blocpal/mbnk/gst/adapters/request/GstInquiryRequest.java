package com.blocpal.mbnk.gst.adapters.request;

import lombok.Data;

@Data
public class GstInquiryRequest {
    private String gstin;
    private Integer cpin;
    private String amount;
}
