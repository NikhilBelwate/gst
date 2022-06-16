package com.blocpal.mbnk.gst.adapters.response;

import com.blocpal.mbnk.gst.model.Bill;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FetchConsumerResponse extends CommonResponse{
    private String message;
    private String amount;
    private String name;
    private String duedate;
    @SerializedName("bill_fetch")
    private Bill bill;
}
