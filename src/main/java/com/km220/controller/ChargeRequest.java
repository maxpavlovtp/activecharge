package com.km220.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class ChargeRequest {

  @NotBlank
  @JsonProperty("station_number")
  private String stationNumber;

  @Positive
  @JsonProperty("period_s")
  private int chargePeriodInSeconds;
}
