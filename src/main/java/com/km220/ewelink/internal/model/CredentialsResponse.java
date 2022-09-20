
package com.km220.ewelink.internal.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.km220.ewelink.internal.login.User;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.beans.factory.annotation.Value;

@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialsResponse {

	@Value("${ewelink.useOAuth}")
	private boolean useOAuth;
	@Value("${ewelink.oAuth-token}")
	private String oAuthToken;

	public String getAt() {
		return useOAuth ? oAuthToken : at;
	}

	@Getter(AccessLevel.NONE)
	private String at;
	private String rt;
	private User user;
	private String region;
	private int error;
	private String msg;
	@Default
	private Map<String, Object> additionalProperties = new HashMap<>();
}
