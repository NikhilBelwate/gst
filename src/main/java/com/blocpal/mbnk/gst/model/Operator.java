package com.blocpal.mbnk.gst.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Introspected
public class Operator {
    @NotEmpty
    @Min(value = 1,message = "Incorrect Operator ID")
    private Integer id;
    @NotEmpty
    private String name;
    private transient  String category;
}
