package com.km220.controller;

import com.km220.PowerAggregationJob;
import com.km220.service.PowerLimitOverloadService;
import com.km220.service.ewelinkspizdeli.model.Status;
import com.km220.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController {

  @Autowired
  private DeviceService deviceService;

  private static final String BOILER_DEVICE_ID = "1001323420";

  // todo use post
  @GetMapping("/start")
  public Response start() throws Exception {
    Status status = deviceService.on();
    return status.getError() > 0 ? Response.fail() : Response.success();
  }

  @GetMapping("/startSecs")
  public Response startSecs(@RequestParam String secs) throws Exception {
    Status status = deviceService.on(Long.parseLong(secs));
    return status.getError() > 0 ? Response.fail() : Response.success();
  }

  @GetMapping("/getChargingDurationLeftSecs")
  public Response getChargeTimeLeftSecs() {
    return new Response("getChargeTimeLeftSecs", deviceService.getChargingDurationLeftSecs());
  }

  @GetMapping("/getChargingStatus")
  public Response getChargingStatus() throws Exception {
    return new Response("kWtCharged", deviceService.getChargedWt()/1000);
  }

  @GetMapping("/getDeviceStatus")
  public Response getDeviceStatus() throws Exception {
    return new Response("getDeviceStatus", deviceService.getDeviceStatus());
  }

  @GetMapping("/isDeviceOn")
  public Response isDeviceOn() throws Exception {
    return new Response("isDeviceOn", deviceService.isDeviceOn());
  }

  //  power limit check
  @Autowired
  PowerLimitOverloadService powerLimitOverloadService;

  @GetMapping("/getPower")
  public Response getPower() throws Exception {
    return new Response("getPower", deviceService.getPower(true));
  }

  @GetMapping("/isPowerLimitOvelrloaded")
  public Response isPowerLimitOvelrloaded() {
    return new Response("isPowerLimitOvelrloaded",
        powerLimitOverloadService.isPowerLimitOvelrloaded());
  }

  @GetMapping("/getPowerLimit")
  public Response getPowerLimit() {
    return new Response("getPowerLimit", powerLimitOverloadService.getPowerLimit());
  }

  @GetMapping("/isOverloadCheckCompleted")
  public Response isOverloadCheckCompleted() {
    return new Response("isOverloadCheckCompleted",
        !PowerAggregationJob.isOn);
  }
}
