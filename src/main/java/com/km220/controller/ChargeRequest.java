package com.km220.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChargeRequest {

  @JsonProperty("station_number")
  private String stationNumber;

  @JsonProperty("period_s")
  private int chargePeriodInSeconds;
}
