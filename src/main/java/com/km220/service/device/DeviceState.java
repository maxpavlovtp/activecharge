package com.km220.service.device;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@ToString
public class DeviceState {

  @NonNull
  private final String deviceId;
  private final boolean switchState;
  private final double power;
  private final double voltage;
}
