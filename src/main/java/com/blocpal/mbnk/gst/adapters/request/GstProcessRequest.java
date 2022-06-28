package com.blocpal.mbnk.gst.adapters.request;

import lombok.Data;

@Data
public class GstProcessRequest {
    private String gibTxnId;
    private Integer cpin;
    private String amount;
    private String reference_number;
}
