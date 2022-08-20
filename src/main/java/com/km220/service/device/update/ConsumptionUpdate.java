package com.km220.service.device.update;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class ConsumptionUpdate extends AbstractDeviceUpdate {

  private final float oneKwh;
}
