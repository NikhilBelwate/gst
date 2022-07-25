package com.blocpal.mbnk.gst.adapters.db.model;

import com.blocpal.common.objectmapper.CustomObjectMapper;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.*;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationServiceProviderInfo {

    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private String gibTxnId;

    @PropertyName("reqD")
    private Map<String, Object> reqData;

    @PropertyName("respD")
    private Map<String, Object> respData;

    @PropertyName("quote")
    private Map<String, Object> quote;

    @PropertyName("reqD")
    public Map<String, Object> getReqData() {
        return reqData;
    }

    @PropertyName("reqD")
    public void setReqData(Map<String, Object> data) {
        this.reqData = data;
    }

    @PropertyName("respD")
    public Map<String, Object> getRespData() {
        return respData;
    }

    @PropertyName("respD")
    public void setRespData(Map<String, Object> data) {
        this.respData = data;
    }

    @PropertyName("quote")
    public Map<String, Object> getQuote() {
        return quote;
    }

    @PropertyName("quote")
    public void setQuote(Map<String, Object> quote) {
        this.quote = quote;
    }

    public Map<String,Object> toMap() {
        return CustomObjectMapper.toMap(this);
    }
}
