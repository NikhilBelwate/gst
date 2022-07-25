package com.blocpal.mbnk.gst.dao.models;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SPSData {


    private String n;


    private String wId;


    private String aT;

    private Map<String,Object> wlts;

    public String getN() {
        return n;
    }

    @PropertyName("wId")
    public String getwId() {
        return wId;
    }

    @PropertyName("aT")
    public String getaT() {
        return aT;
    }

    public Map<String, Object> getWlts() {
        return wlts;
    }

    public void setaT(String aT) {
        this.aT = aT;
    }
}
