package com.blocpal.mbnk.gst.dao.models;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillerData {

    private String oId;

    @PropertyName("oId")
    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }
}
