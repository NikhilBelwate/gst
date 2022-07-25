package com.blocpal.mbnk.gst.adapters.db.model;

import com.google.cloud.firestore.annotation.PropertyName;
import io.micronaut.core.annotation.Introspected;
import lombok.NoArgsConstructor;

@Introspected
@NoArgsConstructor
public class IntegrationCustInfo {
    private String gstNo;
    private String cpin;
    private String apiName;

    @PropertyName("gstNo")
    public String getGstNo() {
        return gstNo;
    }

    @PropertyName("gstNo")
    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }
    @PropertyName("cpin")
    public String getCpin() {
        return cpin;
    }
    @PropertyName("cpin")
    public void setCpin(String cpin) {
        this.cpin = cpin;
    }
    @PropertyName("apiN")
    public String getApiName() {
        return apiName;
    }
    @PropertyName("apiN")
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }
}
