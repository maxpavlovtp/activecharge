package com.km220.controller;

import com.km220.service.ewelink.model.Status;
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

  // todo use post
  @GetMapping("/start")
  public Response start() throws Exception {
    Status status = deviceService.on(20);
    return status.getError() > 0 ? Response.fail() : Response.success();
  }

  @GetMapping("/startSecs")
  public Response startSecs(@RequestParam String secs) throws Exception {
    Status status = deviceService.on(Integer.parseInt(secs));
    return status.getError() > 0 ? Response.fail() : Response.success();
  }

  @GetMapping("/getChargingDurationLeftSecs")
  public Response getChargeTimeLeftSecs() throws Exception {
    return new Response("getChargeTimeLeftSecs", deviceService.getChargingDurationLeftSecs());
  }

  @GetMapping("/getChargingStatus")
  public Response getChargingStatus() throws Exception {
    return new Response("kWtCharged", deviceService.getChargedWt());
  }

  @GetMapping("/getDeviceStatus")
  public Response getDeviceStatus() throws Exception {
    return new Response("getDeviceStatus", deviceService.getDeviceStatus());
  }

  @GetMapping("/isDeviceOn")
  public Response isDeviceOn() throws Exception {
    return new Response("isDeviceOn", deviceService.isDeviceOn());
  }
}
