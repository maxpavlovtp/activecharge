package com.km220.controller;

import com.km220.PowerAggregationJob;
import com.km220.model.DeviceStatus;
import com.km220.service.DeviceService;
import com.km220.service.PowerLimitOverloadService;
import com.km220.service.ewelink.EwelinkDeviceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class ChargerDeviceController {

  //TODO; refactor
  @Value("${device.chargeTimeSecs}")
  private int chargeTimeSecs;

  //TODO: refactor
  @Value("${deviceId}")
  private String deviceId;

  private final DeviceService deviceService;
  private final PowerLimitOverloadService powerLimitOverloadService;

  public ChargerDeviceController(final EwelinkDeviceService deviceService,
      PowerLimitOverloadService powerLimitOverloadService) {
    this.deviceService = deviceService;
    this.powerLimitOverloadService = powerLimitOverloadService;
  }

  //TODO: GET HTTP method should not change resource state.
  @GetMapping("/start")
  public ChargerResponse<Void> start() {
    deviceService.toggleOn(deviceId, chargeTimeSecs);
    return ChargerResponse.success();
  }

  @GetMapping("/startSecs")
  public ChargerResponse<Void> startSecs(@RequestParam String secs) {
    deviceService.toggleOn(deviceId, Integer.parseInt(secs));
    return ChargerResponse.success();
  }

  @GetMapping("/getChargingDurationLeftSecs")
  public ChargerResponse<Long> getChargeTimeLeftSecs() {
    return new ChargerResponse<>("getChargeTimeLeftSecs",
        deviceService.getChargingDurationLeftSecs());
  }

  @GetMapping("/getChargingStatus")
  public ChargerResponse<Float> getChargingStatus() {
    return new ChargerResponse<>("kWtCharged", deviceService.getChargedWt() / 1000);
  }

  @GetMapping("/getDeviceStatus")
  public ChargerResponse<DeviceStatus> getDeviceStatus() {
    var deviceStatus = deviceService.getStatus(deviceId);
    return new ChargerResponse<>("getDeviceStatus", deviceStatus);
  }

  @GetMapping("/isDeviceOn")
  public ChargerResponse<Boolean> isDeviceOn() {
    var deviceStatus = deviceService.getStatus(deviceId);
    return new ChargerResponse<>("isDeviceOn", deviceStatus.isSwitchState());
  }

  @GetMapping("/getPower")
  public ChargerResponse<Double> getPower() {
    var deviceStatus = deviceService.getStatus(deviceId);
    return new ChargerResponse<>("getPower", deviceStatus.getPower());
  }

  @GetMapping("/isPowerLimitOvelrloaded")
  public ChargerResponse<Boolean> isPowerLimitOvelrloaded() {
    return new ChargerResponse<>("isPowerLimitOvelrloaded",
        powerLimitOverloadService.isPowerLimitOvelrloaded());
  }

  @GetMapping("/getPowerLimit")
  public ChargerResponse<Integer> getPowerLimit() {
    return new ChargerResponse<>("getPowerLimit", powerLimitOverloadService.getPowerLimit());
  }

  @GetMapping("/isOverloadCheckCompleted")
  public ChargerResponse<Boolean> isOverloadCheckCompleted() {
    return new ChargerResponse<>("isOverloadCheckCompleted",
        !PowerAggregationJob.isOn);
  }
}
