package com.blocpal.mbnk.gst.adapters.wallets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletInfo {
	
	String type;
	
	String walletId;
}
