package com.km220.service.device.update;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class DeviceStateUpdate extends AbstractDeviceUpdate {

  private boolean online;
}
