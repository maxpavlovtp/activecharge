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
public class Family {

  private String id;
  private Integer index;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
