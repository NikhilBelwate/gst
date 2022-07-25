package com.blocpal.mbnk.gst.response;

import com.blocpal.mbnk.gst.model.TransactionDetail;
import com.google.cloud.firestore.annotation.PropertyName;
import lombok.Builder;

import java.util.List;
@Builder
public class GstInquiryResponse {
    private String gibTxnId;
    private String cpin;
    private String amount;
    private String referenceNumber;
    public List<TransactionDetail> transactionDetails;
    private String gstin;
    private String apiName;
    private String txnId;
    private Boolean isNew;

}
