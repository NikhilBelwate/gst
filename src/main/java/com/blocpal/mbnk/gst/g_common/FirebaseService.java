package com.blocpal.mbnk.gst.g_common;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Singleton
public class FirebaseService {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseService.class);

    private FirebaseApp app = null ;

    @PostConstruct
    public void initialize() throws IOException {
        logger.info("---> Initializing FirebaseService Service");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();

        this.app =FirebaseApp.initializeApp(options);

        //logger.info(app.getName());

        logger.info("<--- Initialized Firebase Service");
    }

    public FirebaseApp getApp() {

        return app;
    }
}
