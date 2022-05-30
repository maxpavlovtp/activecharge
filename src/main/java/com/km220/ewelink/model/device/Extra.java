package com.km220.ewelink.model.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Extra {

  private Extra_ extra;
  private String id;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
