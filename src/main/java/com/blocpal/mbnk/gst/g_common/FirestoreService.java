package com.blocpal.mbnk.gst.g_common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Singleton
public class FirestoreService {
    private static final Logger logger = LoggerFactory.getLogger(FirestoreService.class);

    private Firestore db = null ;

    @PostConstruct
    public void initialize() throws IOException {
        logger.info("---> Initializing Firestore Service");

        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance().toBuilder()
                        .setCredentials(GoogleCredentials.getApplicationDefault())
                        .build();
        this.db = firestoreOptions.getService();

        logger.info("<--- Initialized Firestore Service");
    }

    public Firestore getDb() {
        return db;
    }

    public String getProjectId() {
        return db.getOptions().getProjectId();
    }
}
