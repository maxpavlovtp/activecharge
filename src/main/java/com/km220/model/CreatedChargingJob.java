package com.km220.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CreatedChargingJob {

  String id;
  @JsonProperty("scan_interval_ms")
  int scanIntervalMs;
}
