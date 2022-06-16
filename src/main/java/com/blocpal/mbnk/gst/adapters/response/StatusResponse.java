package com.blocpal.mbnk.gst.adapters.response;

import com.blocpal.mbnk.gst.model.Transaction;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class StatusResponse extends CommonResponse{
    private String message;
    @SerializedName("data")
    private Transaction data;
}
