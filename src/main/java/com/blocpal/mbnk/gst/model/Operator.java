package com.blocpal.mbnk.gst.model;

import lombok.Data;

@Data
public class Operator {
    private String id;
    private String name;
    private transient  String category;
}
