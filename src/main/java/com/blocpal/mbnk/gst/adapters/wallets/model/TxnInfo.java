package com.blocpal.mbnk.gst.adapters.wallets.model;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

@Builder
@Introspected
@Data
public class TxnInfo {
    CustInfo cust;
}
