package com.blocpal.mbnk.gst.adapters.wallets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    private String authId;
    private String type;
    private UserProfile profile;
    private List<WalletInfo> wallets;
}
