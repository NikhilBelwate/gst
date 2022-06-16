package com.blocpal.mbnk.gst.adapters.wallets;


import com.blocpal.mbnk.gst.adapters.wallets.exception.GetUserInfoFailed;
import com.blocpal.mbnk.gst.adapters.wallets.exception.GetUserKycDocImageFailed;
import com.blocpal.mbnk.gst.adapters.wallets.exception.TxnInitiateFailed;
import com.blocpal.mbnk.gst.adapters.wallets.exception.TxnUpdateFailed;
import com.blocpal.mbnk.gst.adapters.wallets.request.InitiateTxnRequest;
import com.blocpal.mbnk.gst.adapters.wallets.request.UpdateTxnRequest;
import com.blocpal.mbnk.gst.g_common.BasicAuth;
import com.blocpal.mbnk.gst.g_common.HTTPClient;
import com.blocpal.mbnk.gst.g_common.HTTPRequestMethod;
import com.blocpal.mbnk.gst.g_common.ServiceResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

    @Slf4j
    public class WalletsApi {
    	
    	
    	private final static String INITIATE_TXN_URI = "/txns/initiateTxn";
        private final static String UPDATE_TXN_URI   = "/txns/updateTxn";
        private final static String GET_USER_INFO  = "/user/{userId}/";
        private final static String GET_USER_KYC_DOC_IMAGE  = "/user/{userId}/kyc/{category}/image";
        private final static String PROD_URL  = "https://walletsapi.e.mbnk.com";
        private final static String UAT_URL   = "https://walletsapi-qa.e.mbnk.com";
        private final static String LOCAL_URL = "http://localhost:8080";

        private BasicAuth auth;

        private Gson gson = new Gson();

        public WalletsApi(String userName, String password) {
            auth = BasicAuth.builder()
                    .userName(userName)
                    .passWord(password)
                    .build();
        }

        /*
         * Gets the Base Url
         */
        private String getBaseUrl () {
            String url = null;
            String projectId = System.getenv("GOOGLE_CLOUD_PROJECT");
            if (projectId == null) {
                url = LOCAL_URL;
            }else if (projectId.equalsIgnoreCase("mbnk-integrations-qa")) {
                url = UAT_URL;
            }else if (projectId.equalsIgnoreCase("mbnk-integrations")) {
                url = PROD_URL;
            }
            return url;
        }

        /*
         *Update Wallet Txn
         */
        public <T> ServiceResult<T> updateWalletTxn(UpdateTxnRequest request) {

            ServiceResult<T> response = null;
            try {

                response = invokeWalletsApi(
                        UPDATE_TXN_URI,
                        request,
                        HTTPRequestMethod.POST);
            }
            catch (IOException | InterruptedException e){
                log.info("Update wallet txn failed");
                throw new TxnUpdateFailed();
            }
            
            return response;
        }

        
        /*
         * Initiate Wallet Txn
         */
        public <T> ServiceResult<T>  initiateWalletTxn (InitiateTxnRequest request) {
            ServiceResult<T> response = null;
            try {
                response = invokeWalletsApi(
                        INITIATE_TXN_URI,
                        request,
                        HTTPRequestMethod.POST);
            }
            catch (IOException | InterruptedException e){
                log.info("Initiate wallet txn failed");
                throw new TxnInitiateFailed();
            }

            return response;
        }


        public <T> ServiceResult<T> getUserInfo(String userId) {
            log.info(String.format("Getting user info for user id : %1$s ", userId));
            String uri = GET_USER_INFO.replace("{userId}", userId);
            ServiceResult<T> response = null;
            try {
                response = invokeWalletsApi(uri, null, HTTPRequestMethod.GET);
                log.info("Response for getUserInfo:"+gson.toJson(response));
            } catch (IOException | InterruptedException e){
                log.error(String.format("Get user info failed with exception: %s", e.getMessage()));
                throw new GetUserInfoFailed();
            }
            if (response.getStatusCode() != 0) {
                log.error(String.format("Get user info failed with status code: %s", response.getStatusCode()));
                throw new GetUserInfoFailed();
            }
            return response;
        }

        public <T> ServiceResult<T> getKYCDocImage(String userId, String category) {
            log.info(String.format("Getting kyc doc image for userid %s, category: %s ", userId, category));
            String uri = GET_USER_KYC_DOC_IMAGE.replace("{userId}", userId).replace("{category}", category);
            ServiceResult<T> response = null;
            try {
                response = invokeWalletsApi(uri, null, HTTPRequestMethod.GET);
                log.info("Response for getUserInfo:"+gson.toJson(response));
            } catch (IOException | InterruptedException e){
                log.error(String.format("Get user kyc doc image failed with exception: %s", e.getMessage()));
                throw new GetUserKycDocImageFailed();
            }
            if (response.getStatusCode() != 0) {
                log.error(String.format("Get user kyc doc image failed with status code: %s", response.getStatusCode()));
                throw new GetUserKycDocImageFailed();
            }
            return response;
        }

        private <T,V> ServiceResult<T> invokeWalletsApi (String uri, V body, HTTPRequestMethod method)
                throws IOException, InterruptedException {

            String bodyStr =  null ;
            if (null != body)
                bodyStr = gson.toJson(body);

            String url = getBaseUrl()+uri;

            log.info("auth:"+gson.toJson(auth));
            log.info(String.format("Request url = %1$s, body = %2$s, method = %3$s",
                    url,bodyStr,method.toString()));

            HTTPClient client = HTTPClient.builder()
                    .auth(auth)
                    .url(url)
                    .method(method)
                    .body(bodyStr)
                    .build();

            String response = client.invoke();
            log.info("Response = "+response);
            ServiceResult<T> serviceResponse = gson.fromJson(response, new TypeToken<ServiceResult<Object>>(){}.getType());
            return serviceResponse;
        }

}
