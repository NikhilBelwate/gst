package com.blocpal.mbnk.gst.g_common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Singleton
public class StorageService {

    private Storage db = null ;

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    @PostConstruct
    public void initialize() throws IOException {
        logger.info("---> Initializing Cloud Storage Service");

        StorageOptions storageOptions =
                StorageOptions.getDefaultInstance().toBuilder()
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();
        this.db = storageOptions.getService();

        logger.info("<--- Initialized Cloud Storage Service");
    }

    public Storage getStorage() {
        return db;
    }


    public String signedUrl(String bucketName, String fileName, Integer timeInMinutes) {

        return db.signUrl(
                BlobInfo.newBuilder(
                        bucketName, fileName
                ).build(),
                timeInMinutes,
                TimeUnit.MINUTES,
                Storage.SignUrlOption.withV4Signature()
        ).toString();
    }
}
