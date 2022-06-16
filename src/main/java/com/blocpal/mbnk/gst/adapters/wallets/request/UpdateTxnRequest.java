package com.blocpal.mbnk.gst.adapters.wallets.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class UpdateTxnRequest {
    private String txnId;
    private String status;
    private String statusMessage;
    Map<String, String> extIds;
}
