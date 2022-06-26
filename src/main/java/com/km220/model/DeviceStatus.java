package com.km220.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@ToString
public class DeviceStatus {

  @NonNull
  private final String deviceId;
  private final boolean switchState;
  private final double power;
  private final double voltage;
}
