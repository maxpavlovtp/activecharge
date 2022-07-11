package com.km220.service.device;

public interface DeviceService {

  DeviceState getState(String deviceId);

  void toggleOn(String deviceId, int chargeTimeSeconds);

  void toggleOff(String deviceId, int chargeTimeSeconds);
}
