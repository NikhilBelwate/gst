package com.blocpal.mbnk.gst.adapters.wallets.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Introspected
public class CustInfo {
    private String name;
    private String mobile;
}
