package com.blocpal.mbnk.gst.request;

import io.micronaut.context.annotation.Bean;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Bean
public class ConsumerDetailsRequest {
    @NotNull
    private Integer operator;
    @NotNull
    private String canumber;

}
