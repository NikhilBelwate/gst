package com.blocpal.mbnk.gst.adapters.wallets;


import com.blocpal.mbnk.gst.adapters.provider.paysprint.PaysprintConstant;
import com.blocpal.mbnk.gst.adapters.wallets.exception.GetUserInfoFailed;
import com.blocpal.mbnk.gst.adapters.wallets.exception.GetUserKycDocImageFailed;
import com.blocpal.mbnk.gst.adapters.wallets.exception.WalletException;
import com.blocpal.mbnk.gst.adapters.wallets.model.UserInfo;
import com.blocpal.mbnk.gst.adapters.wallets.request.InitiateTxnRequest;
import com.blocpal.mbnk.gst.adapters.wallets.request.UpdateTxnRequest;
import com.blocpal.mbnk.gst.g_common.IdGenUtility;
import com.blocpal.mbnk.gst.g_common.SecretManagerService;
import com.blocpal.mbnk.gst.g_common.ServiceResult;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class WalletService {

    private WalletsApi walletsApi = null;
    private Gson gson = new Gson();

    @Inject
    private SecretManagerService secretManagerService;

    @PostConstruct
    public void initialize() {
        walletsApi = initWalletsApi();
    }

    private WalletsApi initWalletsApi() {
        JSONObject jsonObject = new JSONObject(secretManagerService.getSecret("wallet"));
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        return new WalletsApi(username, password);
    }

    public UserInfo getUserInfo(String userId) {
        log.info(String.format("getUserInfo request for userid: %s", userId));
        ServiceResult<Object> result = walletsApi.getUserInfo(userId);
        if(result == null){
            throw new GetUserInfoFailed();
        }
        UserInfo userInfo = gson.fromJson(gson.toJson(result.getData()), UserInfo.class);
        if(userInfo == null){
            throw new GetUserInfoFailed();
        }
        return userInfo;
    }

    public String getUserDoc(String userId, String category) {
        log.info(String.format("getUserDoc request for userid: %s , category: %s", userId, category));
        ServiceResult<Object> result = walletsApi.getKYCDocImage(userId, category);
        if(result == null){
            throw new GetUserKycDocImageFailed();
        }
        String signedUrl = gson.fromJson(gson.toJson(result.getData()), String.class);
        if(signedUrl == null){
            throw new GetUserKycDocImageFailed();
        }
        return signedUrl;
    }
    
    public String  initiateOnboardingCharges(UserInfo info) {    	
        log.info("--> initiateOnboardingCharges");

    	Map<String,String> wallets = new HashMap<>();
    	wallets.put("retailer", getWalletId(info));
    
    	String txnId = IdGenUtility.getId();
        InitiateTxnRequest initiateTxnRequest = InitiateTxnRequest.builder()
                .txnId(txnId)
                .clientTxnId(txnId)
                .wallets(wallets)
                .type(PaysprintConstant.TXN_TYPE)
                .subType(PaysprintConstant.TXN_SUB_TYPE)
                .cur(PaysprintConstant.DEFAULT_CUR)
                .txnAmt(0.0)
                .build();

        ServiceResult<Object> result = walletsApi.initiateWalletTxn(
        		initiateTxnRequest);        
        if (result.getStatusCode() != 0) {
        	throw new WalletException(result.getStatusCode(),
        			result.getMessage());
        }        
        log.info("<-- initiateOnboardingCharges"+result.toString());        
        return txnId;
    }
    
    public ServiceResult<Object> updateOnboardingCharges(String txnId, String status){
        log.info("--> updateOnboardingCharges");
        
        UpdateTxnRequest updateTxnRequest = UpdateTxnRequest.builder()
                .txnId(txnId)
                .status(status)
                .statusMessage(status)
                .build();

        log.info("Updating txn in wallet");
        ServiceResult<Object> result = walletsApi.updateWalletTxn(
        		updateTxnRequest);
        
        log.info("<-- updateOnboardingCharges"+result.toString());
        return result;
    }
    
    private String getWalletId (UserInfo info) {
		
    	log.info("-> Get Wallet Id ");
    	
    	String userType = info.getType();
    	
    	String walletId= info.getWallets().stream()
    	.filter(item->item.getType().equalsIgnoreCase(userType))
    	.map(item-> item.getWalletId())
    	.findFirst().orElse(null);
    	
    	log.info("<-- Get Wallet Id "+ walletId);
    	    	
    	return walletId;
    }
}
