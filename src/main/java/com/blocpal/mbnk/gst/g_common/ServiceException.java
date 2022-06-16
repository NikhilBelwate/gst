package com.blocpal.mbnk.gst.g_common;

import lombok.Getter;
import lombok.Setter;

public class ServiceException extends RuntimeException{

    @Getter
    @Setter
    private Integer statusCode;

    @Getter@Setter
    private String message;

    @Getter@Setter
    private Boolean logError = true;
}
