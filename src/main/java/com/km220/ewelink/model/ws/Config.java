
package com.km220.ewelink.model.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Config {

  private Integer hb;
  private Integer hbInterval;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
