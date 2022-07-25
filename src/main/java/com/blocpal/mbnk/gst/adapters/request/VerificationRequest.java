package com.blocpal.mbnk.gst.adapters.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class VerificationRequest {
    @SerializedName("reference_number")
    private String referenceNumber;
}
