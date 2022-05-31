
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
public class Info {

  private String token;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();

}
