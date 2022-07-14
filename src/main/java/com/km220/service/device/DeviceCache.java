package com.km220.service.device;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeviceCache {

//todo implement mapping
  @Value("${deviceId}")
  private String deviceId;

  public static volatile boolean isOn;
  public static volatile float voltage;
  public static volatile float powerWt;
  public static volatile float chargedWt;
  public static volatile float chargingWtAverageWtH;

  public DeviceState getDeviceStatus() {
    return  DeviceState.builder()
        .deviceId(deviceId)
        .switchState(isOn)
        .power(powerWt)
        .voltage(voltage)
        .build();
  }
}
