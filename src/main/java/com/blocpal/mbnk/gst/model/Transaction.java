package com.blocpal.mbnk.gst.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Introspected
public class Transaction {

    @NotEmpty
    public String amount;
    @NotNull
    public String cpin;
    @NotNull
    public String apiName;
    @NotNull
    public String gstin;
    @NotEmpty
    public List<TransactionDetail> transactionDetails;

}
