package com.km220.service.device.update;

public interface DeviceUpdater {

  void onConsumption(ConsumptionUpdate consumptionUpdate);

  void onStatus(DeviceStatusUpdate deviceStatusUpdate);
}
