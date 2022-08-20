package com.km220.service.device.update;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class DeviceStatusUpdate extends AbstractDeviceUpdate {

  private float power;
  private float voltage;
  private boolean on;
}
