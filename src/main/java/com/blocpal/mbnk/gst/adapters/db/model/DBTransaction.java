package com.blocpal.mbnk.gst.adapters.db.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class DBTransaction {

    @DocumentId
    @Getter
    @Setter
    private String id;
    private String amount;

    private String status;
    private String type;
    private String subType;
    private String requestId;
    private String invoiceLink;

    private Date dateCreated;
    private Date dateUpdated;

    private IntegrationCustInfo custInfo;
    private InternalUserInfo internalUserInfo;
    private IntegrationServiceProviderInfo serviceProviderInfo;


    @PropertyName("a")
    public String getAmount() {
        return amount;
    }

    @PropertyName("a")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @PropertyName("s")
    public String getStatus() {
        return status;
    }

    @PropertyName("s")
    public void setStatus(String status) {
        this.status = status;
    }

    @PropertyName("t")
    public String getType() {
        return type;
    }

    @PropertyName("t")
    public void setType(String type) {
        this.type = type;
    }

    @PropertyName("st")
    public String getSubType() {
        return subType;
    }

    @PropertyName("st")
    public void setSubType(String subType) {
        this.subType = subType;
    }

    @PropertyName("reqId")
    public String getRequestId() {
        return requestId;
    }

    @PropertyName("reqId")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @PropertyName("ts")
    public Date getDateCreated() {
        return dateCreated;
    }

    @PropertyName("ts")
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @PropertyName("uts")
    public Date getDateUpdated() {
        return dateUpdated;
    }

    @PropertyName("uts")
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @PropertyName("cus")
    public IntegrationCustInfo getCustInfo() {
        return custInfo;
    }

    @PropertyName("cus")
    public void setCustInfo(IntegrationCustInfo custInfo) {
        this.custInfo = custInfo;
    }

    @PropertyName("u")
    public InternalUserInfo getInternalUserInfo() {
        return internalUserInfo;
    }

    @PropertyName("u")
    public void setInternalUserInfo(InternalUserInfo internalUserInfo) {
        this.internalUserInfo = internalUserInfo;
    }

    @PropertyName("sp")
    public IntegrationServiceProviderInfo getServiceProviderInfo() {
        return serviceProviderInfo;
    }

    @PropertyName("sp")
    public void setServiceProviderInfo(IntegrationServiceProviderInfo serviceProviderInfo) {
        this.serviceProviderInfo = serviceProviderInfo;
    }

    @PropertyName("invoice")
    public String getInvoiceLink() {
        return invoiceLink;
    }

    @PropertyName("invoice")
    public void setInvoiceLink(String invoiceLink) {
        this.invoiceLink = invoiceLink;
    }
}
