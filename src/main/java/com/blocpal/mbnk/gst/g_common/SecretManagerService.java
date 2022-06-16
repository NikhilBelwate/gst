package com.blocpal.mbnk.gst.g_common;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;

@Singleton
public class SecretManagerService {
	private static final Logger logger = LoggerFactory.getLogger(SecretManagerService.class);
	private static final String LATEST_VERSION ="latest";
	private static final String GOOGLE_CLOUD_PROJECT = "GOOGLE_CLOUD_PROJECT";

	SecretManagerServiceClient client = null;

	@PostConstruct
	public void initialize() throws IOException{
		logger.info("---> Initializing Secret Manager Service");
		client = SecretManagerServiceClient.create();
		logger.info("<--- Initialized Secret Manager Service");
	}

	/*
	 * Get the Secret given the Name
	 */
	public String getSecret(String name) {
		Long startTime = System.nanoTime();

		String projectId = System.getenv(GOOGLE_CLOUD_PROJECT);
		logger.info("projectId:"+projectId);
		if (null == projectId) {
			logger.info("If you are runnning locally define "
					+ "GOOGLE_CLOUD_PROJECT environment variable");
		}

		SecretVersionName  secretName = SecretVersionName.of(
				projectId, name, LATEST_VERSION );
		AccessSecretVersionResponse response = client.accessSecretVersion(secretName);
		String result = response.getPayload().getData().toStringUtf8();

		Long endTime = System.nanoTime();
		logger.info("Time taken to access key:"+(endTime-startTime));
		return result;
	}

	public Map<String,String> getSecretMap(String name){
		String payload = getSecret(name);
		Map<String,String> keys = new Gson().fromJson(payload,
				new TypeToken<Map<String,String>>(){}.getType());

		return keys;
	}

	@PreDestroy
	public void shutdown() throws IOException{
		logger.info("---> Shutting down Secret Manager Service");

		if (null != client)
			client.close();

		logger.info("<--Shutting down Secret Manager Service");
	}
}