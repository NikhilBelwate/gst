package com.blocpal.mbnk.gst.adapters.wallets.model;

import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @PropertyName("mobile")
    private Long mobile;

    @PropertyName("pan")
    private String pan;

    @PropertyName("mobile")
    public Long getMobile() {
        return mobile;
    }

    @PropertyName("mobile")
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    @PropertyName("pan")
    public String getPan() {
        return pan;
    }

    @PropertyName("pan")
    public void setPan(String pan) {
        this.pan = pan;
    }
}