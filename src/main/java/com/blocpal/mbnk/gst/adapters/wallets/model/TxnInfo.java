package com.blocpal.mbnk.gst.adapters.wallets.model;

import com.blocpal.common.wallet.request.RechargeInfo;
import com.blocpal.mbnk.gst.dao.models.GSTData;
import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

@Builder
@Introspected
@Data
public class TxnInfo {
    String gstin;
    String cpin;
    String retailerId;
}
