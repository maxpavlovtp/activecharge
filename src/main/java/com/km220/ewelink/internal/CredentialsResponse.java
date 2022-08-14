
package com.km220.ewelink.internal;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.km220.ewelink.internal.login.User;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialsResponse {

  private String at;
  private String rt;
  private User user;
  private String region;
  private int error;
  private String msg;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
