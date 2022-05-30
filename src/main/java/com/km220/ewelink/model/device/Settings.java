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
public class Settings {

  private Integer opsNotify;
  private Integer opsHistory;
  private Integer alarmNotify;
  private Integer wxAlarmNotify;
  private Integer wxOpsNotify;
  private Integer wxDoorbellNotify;
  private Integer appDoorbellNotify;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
