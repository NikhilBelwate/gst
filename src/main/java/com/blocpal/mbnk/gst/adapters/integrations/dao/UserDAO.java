package com.blocpal.mbnk.gst.adapters.integrations.dao;

import com.blocpal.mbnk.gst.adapters.integrations.model.IntegrationsUser;
import com.blocpal.mbnk.gst.g_common.DatabaseException;
import com.blocpal.mbnk.gst.g_common.FirestoreService;
import com.blocpal.mbnk.gst.g_common.FirestoreUtility;
import com.google.cloud.firestore.Firestore;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class UserDAO {

    private static final String USER_COLLECTION = "us";
    @Inject
    private FirestoreService dbSrvc;
    private Firestore db = null;
    private FirestoreUtility dbUtility = null;

    @PostConstruct
    public void initialize() throws IOException {
        log.info("---> Initializing UserDAO");
        db = dbSrvc.getDb();
        dbUtility = new FirestoreUtility(db);
        log.info("<--- Initialized UserDAO");
    }

    public IntegrationsUser getUser(String serviceType, String providerName, String userId){
        log.info(String.format("getting user for serviceType: %s, providername: %s, userId: %s", serviceType, providerName, userId));
        String providerInfoCollectionKey = String.format("%s_%s_%s", USER_COLLECTION, serviceType, providerName);
        String serviceProviderCollectionPath = String.format("%s/%s/%s", USER_COLLECTION, userId, providerInfoCollectionKey);
        String providerInfoDocId = String.format("%s#%s", serviceType.toUpperCase(), providerName.toUpperCase());
        try {
            IntegrationsUser userInfo = dbUtility.get(
                    serviceProviderCollectionPath,
                    providerInfoDocId,
                    IntegrationsUser.class
            );
            return userInfo;
        }
        catch (InterruptedException | ExecutionException e) {
            log.error(String.format("Error getting user from integrations error: %s", e.getMessage()));
            throw new DatabaseException(e);
        }
    }

    public void updateUser(String serviceType, String providerName, String userId, Map<String, Object> data) {
        log.info(String.format("updating user for serviceType: %s, providername: %s, userId: %s with data: %s", serviceType, providerName, userId, data.toString()));
        String providerInfoCollectionKey = String.format("%s_%s_%s", USER_COLLECTION, serviceType, providerName);
        String serviceProviderCollectionPath = String.format("%s/%s/%s", USER_COLLECTION, userId, providerInfoCollectionKey);
        String providerInfoDocId = String.format("%s#%s", serviceType.toUpperCase(), providerName.toUpperCase());
        try {
            log.info("Update user");
            dbUtility.merge(
                    serviceProviderCollectionPath,
                    data,
                    providerInfoDocId
            );
        }
        catch (ExecutionException | InterruptedException e) {
            log.error(String.format("Error updating user in integrations error: %s", e.getMessage()));
            throw new DatabaseException(e);
        }
    }

}
