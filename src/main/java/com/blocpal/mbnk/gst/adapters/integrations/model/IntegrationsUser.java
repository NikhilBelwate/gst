package com.blocpal.mbnk.gst.adapters.integrations.model;

import com.blocpal.common.objectmapper.CustomObjectMapper;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IntegrationsUser {

    @PropertyName("name")
    private String name;

    @PropertyName("mobile")
    private String mobile;

    @PropertyName("email")
    private String email;

    @PropertyName("address")
    private String address;

    @PropertyName("state")
    private String state;

    @PropertyName("district")
    private String district;

    @PropertyName("city")
    private String city;

    @PropertyName("pincode")
    private String pincode;

    @PropertyName("panNumber")
    private String panNumber;

    @PropertyName("aadhaarNo")
    private String aadhaarNo;

    @PropertyName("aadhaarFront")
    private String aadhaarFront;

    @PropertyName("aadhaarBack")
    private String aadhaarBack;

    @PropertyName("panImage")
    private String panImage;

    @PropertyName("clientRegComplete")
    private Boolean clientRegComplete;

    @PropertyName("p")
    private String password;

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("mobile")
    public String getMobile() {
        return mobile;
    }

    @PropertyName("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("address")
    public String getAddress() {
        return address;
    }

    @PropertyName("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @PropertyName("state")
    public String getState() {
        return state;
    }

    @PropertyName("state")
    public void setState(String state) {
        this.state = state;
    }

    @PropertyName("district")
    public String getDistrict() {
        return district;
    }

    @PropertyName("district")
    public void setDistrict(String district) {
        this.district = district;
    }

    @PropertyName("city")
    public String getCity() {
        return city;
    }

    @PropertyName("city")
    public void setCity(String city) {
        this.city = city;
    }

    @PropertyName("pincode")
    public String getPincode() {
        return pincode;
    }

    @PropertyName("pincode")
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @PropertyName("panNumber")
    public String getPanNumber() {
        return panNumber;
    }

    @PropertyName("panNumber")
    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    @PropertyName("aadhaarNo")
    public String getAadhaarNo() {
        return aadhaarNo;
    }

    @PropertyName("aadhaarNo")
    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    @PropertyName("aadhaarFront")
    public String getAadhaarFront() {
        return aadhaarFront;
    }

    @PropertyName("aadhaarFront")
    public void setAadhaarFront(String aadhaarFront) {
        this.aadhaarFront = aadhaarFront;
    }

    @PropertyName("aadhaarBack")
    public String getAadhaarBack() {
        return aadhaarBack;
    }

    @PropertyName("aadhaarBack")
    public void setAadhaarBack(String aadhaarBack) {
        this.aadhaarBack = aadhaarBack;
    }

    @PropertyName("clientRegComplete")
    public Boolean getClientRegComplete() {
        return clientRegComplete;
    }

    @PropertyName("clientRegComplete")
    public void setClientRegComplete(Boolean clientRegComplete) {
        this.clientRegComplete = clientRegComplete;
    }

    @PropertyName("panImage")
    public String getPanImage() {
        return panImage;
    }

    @PropertyName("panImage")
    public void setPanImage(String panImage) {
        this.panImage = panImage;
    }

    @PropertyName("p")
    public String getPassword() {
        return password;
    }

    @PropertyName("p")
    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String,Object> toMap() {
        Map<String,Object> result = CustomObjectMapper.toMap(this);
        return result;
    }
}

