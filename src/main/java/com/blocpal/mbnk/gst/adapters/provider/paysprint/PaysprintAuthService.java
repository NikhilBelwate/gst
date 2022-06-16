package com.blocpal.mbnk.gst.adapters.provider.paysprint;

import com.blocpal.mbnk.gst.adapters.integrations.dao.UserDAO;
import com.blocpal.mbnk.gst.adapters.request.GstRequestHeader;
import com.blocpal.mbnk.gst.adapters.wallets.WalletService;
import com.blocpal.mbnk.gst.g_common.IdGenUtility;
import com.blocpal.mbnk.gst.g_common.SecretManagerService;
import com.blocpal.mbnk.gst.g_common.StorageService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.time.Instant;
import java.util.Map;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Slf4j
@Singleton
public class PaysprintAuthService {

    @Inject
    WalletService walletService;
    
    @Inject
    StorageService storageService;
    
    @Inject
    UserDAO userDAO;

    @Inject
    private SecretManagerService secretManagerService;

    @Inject
    private GstRequestHeader gstRequestHeader;

    private String partnerId;
    private String secretKey;

    private String projectId;

    private String baseUrl;


    @PostConstruct
    public void initialize() {
        Map<String,String> paysprintKeys = secretManagerService.getSecretMap(PaysprintConstant.PROVIDER_NAME);
        partnerId = paysprintKeys.get("partnerId");
        secretKey = paysprintKeys.get("secretKey");
        projectId = System.getenv("GOOGLE_CLOUD_PROJECT");
        if (projectId.equalsIgnoreCase("mbnk-integrations-qa")) {
            baseUrl = PaysprintEndpoints.baseUATURL;
            gstRequestHeader.setAuthorisedkey("Njc1MzlkZmNkODRiNzhlMzBhM2VkNWFkYzhmYWQyODM=");
        }else if (projectId.equalsIgnoreCase("mbnk-integrations")) {
            baseUrl = PaysprintEndpoints.basePRODURL;
            gstRequestHeader.setAuthorisedkey("");
        }
        gstRequestHeader.setAcceptType(APPLICATION_JSON);
        gstRequestHeader.setContentType(APPLICATION_JSON);
        gstRequestHeader.setToken(createToken());
    }

    private Request getJsonRequest(String uri, RequestBody body) {
        Request.Builder builder = new Request.Builder();
        log.info("url :: {}",baseUrl+uri);
        builder.url(baseUrl + uri);

        Headers headerBuild = Headers.of(gstRequestHeader.getHeaders());
        builder.headers(headerBuild);
        builder.post(body);
        return builder.build();
    }
    public String createToken() {
        try {
            String jws = Jwts.builder()

                    .setHeaderParam("typ","JWT")
                    .claim("timestamp", Instant.now().getEpochSecond())
                    .claim("partnerId", partnerId)
                    .claim("reqid", IdGenUtility.getId())
                    .signWith(
                            SignatureAlgorithm.HS256,
                            secretKey.getBytes("UTF-8")
                    )

                    .compact();
            return jws;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public Map<String, String> getHeaders(){
        return gstRequestHeader.getHeaders();
    }

}
