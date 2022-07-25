package com.blocpal.mbnk.gst.adapters.provider.paysprint;

import com.blocpal.common.client.HTTPRequestMethod;
import com.blocpal.mbnk.gst.adapters.response.CommonResponse;
import com.blocpal.mbnk.gst.exception.GstException;
import com.google.gson.Gson;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Singleton
public class PaysprintClient {
    Gson gson = new Gson();
    private OkHttpClient getClient() {
        return new OkHttpClient
                .Builder()
                .connectTimeout(PaysprintConstant.TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(PaysprintConstant.TIMEOUT_SECS, TimeUnit.SECONDS)
                .writeTimeout(PaysprintConstant.TIMEOUT_SECS, TimeUnit.SECONDS)
                .build();
    }

    private String getBaseURL(){
        String url = null;
        String projectId = System.getenv("GOOGLE_CLOUD_PROJECT");
        if (projectId == null) {
            url = PaysprintEndpoints.baseLocalURL;
        }else if (projectId.equalsIgnoreCase("mbnk-integrations-qa")) {
            url = PaysprintEndpoints.baseUATURL;
        }else if (projectId.equalsIgnoreCase("mbnk-integrations")) {
            url = PaysprintEndpoints.basePRODURL;
        }
        log.debug("gst API Base URL: "+url);
        return url;
    }
    private Request getJsonRequest(HTTPRequestMethod method, String uri, RequestBody body, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        builder.url(getBaseURL() + uri);
        if(headers == null){
            headers = new HashMap<>();
        }
        Headers headerbuild = Headers.of(headers);
        builder.headers(headerbuild);
        if(method == HTTPRequestMethod.GET){
            builder.get();
        }else if(method == HTTPRequestMethod.POST){
            builder.post(body);
        }
        return builder.build();
    }

    public CommonResponse invokeJsonRequest(
            HTTPRequestMethod method,
            String uri,
            String body,
            Map<String, String> headers,
            Type tokenType
    ) throws GstException {
        log.debug(String.format("calling gst api ->  uri: %s, body: %s", uri, body));
        RequestBody requestBody = RequestBody.create(body, MediaType.get("application/json; charset=utf-8"));
        Request request = getJsonRequest(method, uri, requestBody, headers);
        try {
            Response response = getClient().newCall(request).execute();
            String responseStr = response.body().string();
            log.debug("response: " + responseStr);
            CommonResponse res = gson.fromJson(responseStr, tokenType);
            return res;
        }
        catch (Exception e){
            throw new GstException(403,"Paysprint Service Provider is not responding. Please try after sometime.",false);
        }
    }

}
