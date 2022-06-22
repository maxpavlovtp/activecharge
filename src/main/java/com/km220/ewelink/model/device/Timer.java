
package com.km220.ewelink.model.device;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.km220.ewelinkspizdeli.model.devices.Do;
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
public class Timer {

  private String mId;
  private String type;
  private String at;
  private String coolkitTimerType;
  private Integer enabled;
  private Do _do;
  private String period;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
