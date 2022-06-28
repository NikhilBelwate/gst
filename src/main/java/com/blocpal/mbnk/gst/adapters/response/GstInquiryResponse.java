package com.blocpal.mbnk.gst.adapters.response;

import com.blocpal.mbnk.gst.model.Transaction;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GstInquiryResponse extends CommonResponse{
    @SerializedName("data")
    private Transaction data;
}
