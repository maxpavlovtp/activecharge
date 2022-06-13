
package com.km220.ewelink.model.ws;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.km220.ewelink.model.device.Params;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class WssResponse {

  private Integer error;
  private String action;
  private String deviceid;
  private String apikey;
  private String userAgent;
  private Params params;
  private String from;
  private Config config;
  private String seq;
  private String sequence;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
