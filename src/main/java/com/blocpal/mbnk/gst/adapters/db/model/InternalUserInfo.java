package com.blocpal.mbnk.gst.adapters.db.model;


import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalUserInfo {

    private String id;
    private String code;
    private String message;
    private String walletId;
    private String mobile;

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("c")
    public String getCode() {
        return code;
    }

    @PropertyName("c")
    public void setCode(String code) {
        this.code = code;
    }

    @PropertyName("wId")
    public String getWalletId() {
        return walletId;
    }

    @PropertyName("wId")
    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    @PropertyName("mNo")
    public String getMobile() {
        return mobile;
    }

    @PropertyName("mNo")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
