package com.km220.service.device;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class ConsumptionUpdate extends DeviceUpdate {

  private final float oneKwh;
}
