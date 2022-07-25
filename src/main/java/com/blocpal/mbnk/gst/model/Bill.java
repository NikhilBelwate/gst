package com.blocpal.mbnk.gst.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Introspected
public class Bill {
    @NotEmpty
    @Min(value = 1, message = "billAmount must be greater then 0")
    private Double billAmount;
    @NotEmpty
    @Min(value = 1, message = "billnetamount must be greater then 0")
    private Double billnetamount;
    private String dueDate;
    @NotEmpty
    @Min(value = 1, message = "maxBillAmount must be greater then 0")
    private Double maxBillAmount;
    private Boolean acceptPayment;
    private Boolean acceptPartPay;
    private String cellNumber;
    private String userName;
}
