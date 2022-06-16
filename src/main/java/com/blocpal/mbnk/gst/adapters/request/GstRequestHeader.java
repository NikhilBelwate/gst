package com.blocpal.mbnk.gst.adapters.request;

import com.google.gson.annotations.SerializedName;
import io.micronaut.context.annotation.Bean;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Bean
public class GstRequestHeader {

    @SerializedName("Accept")
    private String acceptType;
    @SerializedName("Content-Type")
    private String contentType;
    @SerializedName("Authorisedkey")
    private String authorisedkey;

    @SerializedName("Token")
    private String token;


    public Map<String,String> getHeaders() {
        Map<String,String> result = new HashMap<>();
        result.put("Accept",acceptType);
        result.put("Content-Type",contentType);
        result.put("Authorisedkey",authorisedkey);
        result.put("Token",token);
        return result;
    }
}
