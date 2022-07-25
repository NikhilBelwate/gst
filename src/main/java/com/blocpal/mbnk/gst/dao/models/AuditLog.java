package com.blocpal.mbnk.gst.dao.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.Timestamp;
import lombok.*;

/**
 * Hash code.
 *
 * @return the int
 */
@Data

/**
 * To string.
 *
 * @return the java.lang. string
 */
@ToString

/**
 * Instantiates a new integration client application log.
 *
 * @param request the request
 * @param response the response
 * @param createOn the create on
 * @param url the url
 * @param updatedOn the updated on
 * @param serviceName the service name
 * @param headers the headers
 */
@AllArgsConstructor

/**
 * Instantiates a new integration client application log.
 */
@NoArgsConstructor

/**
 * To string.
 *
 * @return the java.lang. string
 */
@Builder(toBuilder = true)
public class AuditLog {
	
	/** The request. */
	@JsonProperty("req")
	private String req;

	/** The response. */
	@JsonProperty("resp")
	private String resp;

	/** The create on. */
	@JsonProperty("ts")
	private Timestamp ts;

	/** The url. */
	@JsonProperty("u")
	private String u;

	/** The updated on. */
	@JsonProperty("uts")
	private Timestamp uts;

	/** The service name. */
	@JsonProperty("s")
	private String s;

}
