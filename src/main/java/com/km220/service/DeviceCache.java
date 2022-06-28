package com.km220.service;

import com.km220.model.DeviceStatus;
import lombok.extern.jackson.Jacksonized;
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

  public DeviceStatus getDeviceStatus() {
    return  DeviceStatus.builder()
        .deviceId(deviceId)
        .switchState(isOn)
        .power(powerWt)
        .voltage(voltage)
        .build();
  }
}
