package com.blocpal.mbnk.gst.adapters.response;

import com.blocpal.mbnk.gst.model.ProcessDetails;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GstProcessResponse extends CommonResponse{
    @SerializedName("data")
    private ProcessDetails data;

}
