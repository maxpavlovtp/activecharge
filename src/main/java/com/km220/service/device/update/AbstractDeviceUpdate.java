package com.km220.service.device.update;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public abstract class AbstractDeviceUpdate {

  @NonNull
  private String deviceId;
  @Default
  private int error = 0;
  @Default
  private String action = null;
  @Default
  private String sequence = null;
}
