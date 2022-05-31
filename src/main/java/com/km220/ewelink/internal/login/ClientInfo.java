
package com.km220.ewelink.internal.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientInfo {

  private String model;
  private String os;
  private String imei;
  private String romVersion;
  private String appVersion;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
