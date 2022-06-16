package com.blocpal.mbnk.gst.g_common;

import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static io.micronaut.http.HttpHeaders.AUTHORIZATION;
import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

@Builder
@Data
public class HTTPClient {

	@Builder.Default
	private Integer timeOutSecs=20;
	private BasicAuth auth;
	private HTTPRequestMethod method;
	private String body;
	private String url;

	@Builder.Default
	private String contentType ="application/json";

	/*
	 * Get Http Client
	 */
	private HttpClient getClient() {
		return HttpClient.newBuilder()
				.version(HttpClient.Version.HTTP_1_1)
				.connectTimeout(Duration.ofSeconds(timeOutSecs))
				.build();
	}

	/*
	 * Get Http Request Object
	 */
	private HttpRequest getRequest() {
		HttpRequest.Builder builder = HttpRequest.newBuilder()
				.setHeader(CONTENT_TYPE, contentType)
				.uri(URI.create(url));
		if (null != auth) {
			String basicAuth = AuthUtility.encodeBase64(auth.getUserName(), auth.getPassWord());
			builder.setHeader(AUTHORIZATION, "Basic "+basicAuth);
		}
		if (method.equals(HTTPRequestMethod.POST)) {
			builder.POST(HttpRequest.BodyPublishers.ofString(body));
		}
		else if (method.equals(HTTPRequestMethod.PATCH)) {
			builder.method("PATCH",HttpRequest.BodyPublishers.ofString(body));
		}
		else {
			builder.GET();
		}
		return builder.build();
	}

	public String invoke() {

		HttpRequest request = getRequest();

		HttpResponse<String> response;
		try {
			response = getClient().send(
					request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			throw new ApiCallException(e);
		}

		return response.body();
	}
}
