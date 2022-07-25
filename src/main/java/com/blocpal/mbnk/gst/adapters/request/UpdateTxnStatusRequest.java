package com.blocpal.mbnk.gst.adapters.request;

import com.google.gson.annotations.SerializedName;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Introspected
public class UpdateTxnStatusRequest {

    @NotBlank(message = "reference Number is required")
    @SerializedName("reference_number")
    private String referenceNumber;
}
