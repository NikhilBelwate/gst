package com.blocpal.mbnk.gst.adapters.response;

import com.blocpal.mbnk.gst.model.Bill;
import com.blocpal.mbnk.gst.model.Transaction;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GstInquiryResponse extends CommonResponse{
    @SerializedName("data")
    private Transaction data;
}
