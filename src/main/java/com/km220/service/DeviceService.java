package com.km220.service;

import com.km220.model.DeviceStatus;

public interface DeviceService {

  //TODO: refactor
  float getChargedWt();

  //TODO: refactor
  long getChargingDurationLeftSecs();

  DeviceStatus getStatus(String deviceId);

  void toggleOn(String deviceId, int chargeTimeSeconds);

  void toggleOff(String deviceId, int chargeTimeSeconds);
}
