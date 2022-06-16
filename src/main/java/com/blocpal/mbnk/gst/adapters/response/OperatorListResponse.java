package com.blocpal.mbnk.gst.adapters.response;

import com.blocpal.mbnk.gst.model.Operator;
import lombok.Data;

import java.util.List;

@Data
public class OperatorListResponse extends CommonResponse {
    private List<Operator> data;
    private String message;
}
