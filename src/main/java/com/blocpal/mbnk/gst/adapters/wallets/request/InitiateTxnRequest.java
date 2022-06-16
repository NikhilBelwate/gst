package com.blocpal.mbnk.gst.adapters.wallets.request;

import com.blocpal.mbnk.gst.adapters.wallets.model.TxnInfo;
import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Introspected
@Data
public class InitiateTxnRequest {
    private String txnId;
    private String clientTxnId;
    private Map<String, String> wallets;
    private String type;
    private String subType;
    private String cur;
    private Double txnAmt;
    private TxnInfo txnInfo;
}
