package com.blocpal.mbnk.gst.dao.models;

import com.blocpal.common.objectmapper.CustomObjectMapper;
import com.blocpal.mbnk.gst.model.ProcessDetails;
import com.blocpal.mbnk.gst.model.Transaction;
import com.blocpal.mbnk.gst.model.TransactionDetail;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
public class GSTData {
    private String gibTxnId;
    private String cpin;
    private String amount;
    private String referenceNumber;
    public List<TransactionDetail> transactionDetails;
    private String gstin;
    private String apiName;
    private String txnId;

    public GSTData(Transaction transaction){
        this.amount=transaction.amount;
        this.apiName=transaction.apiName;
        this.cpin=transaction.cpin;
        this.gstin=transaction.gstin;
        if(transaction.transactionDetails.size()>0){
            //This can change
            this.gibTxnId=transaction.transactionDetails.get(0).gibTxnId;
        }
        this.transactionDetails=transaction.getTransactionDetails();
    }

    public Map<String, Object> toMap() {
        return CustomObjectMapper.toMap(this);
    }
}
