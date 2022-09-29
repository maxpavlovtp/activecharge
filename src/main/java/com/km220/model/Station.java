package com.km220.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Station {

  private final String name;
  private final String number;
  private final String deviceId;
  private final ChargingJob activeJob;
}
