package com.km220.service;

import static com.km220.PowerAggregationJob.chargedWt;
import static com.km220.PowerAggregationJob.onTime;
import static com.km220.service.PowerLimitOverloadService.OVERLOAD_LIMIT_TIMER_SECS;
import static java.lang.System.currentTimeMillis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.km220.PowerAggregationJob;
import com.km220.ewelink.EwelinkClient;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.model.device.Device;
import com.km220.service.ewelink.EweLink;
import com.km220.service.ewelink.model.Status;
import com.km220.service.ewelink.model.devices.DeviceItem;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

  @Value("${ewelink.region}")
  private String region;
  @Value("${ewelink.email}")
  private String email;
  @Value("${ewelink.password}")
  private String password;

  @Value("${deviceId}")
  private String deviceId;

  private EweLink eweLinkLegacy;

  private EwelinkClient eweLink;
  public static boolean newApiForGetPower = false;

  @PostConstruct
  public void init() throws Exception {
    eweLinkLegacy = new EweLink(region, email, password, 60);
    eweLinkLegacy.login();

    eweLink = EwelinkClient.builder()
        .parameters(new EwelinkParameters(region, email, password))
        .build();
  }

  public Status on() throws Exception {
    return on(OVERLOAD_LIMIT_TIMER_SECS + 10);
  }

  public Status on(long chargeSeconds) throws Exception {
    // todo fix
    eweLinkLegacy.login();

    PowerAggregationJob.chargingDurationSecs = 0;
    PowerAggregationJob.chargeDurationSecs = chargeSeconds;
    PowerAggregationJob.chargedWt = 0;
    PowerAggregationJob.onTime = currentTimeMillis();
    PowerAggregationJob.offTime = onTime + 1000L * PowerAggregationJob.chargeDurationSecs;

    Status status = eweLinkLegacy.setDeviceStatus(deviceId, "on");
    PowerAggregationJob.isOn = true;
    return status;
  }

  public boolean isDeviceOn() throws Exception {
    return getDeviceStatus().contains("_switch='on'");
  }

  public long getChargingDurationLeftSecs() {
    return PowerAggregationJob.chargingDurationLeftSecs;
  }

  public Status off() throws Exception {
    Status result;
    try {
      result = eweLinkLegacy.setDeviceStatus(deviceId, "off");
    } catch (Exception e) {
      // todo fix login hack
      eweLinkLegacy.login();
      result = eweLinkLegacy.setDeviceStatus(deviceId, "off");
    }

    System.out.println("Charge has been finished");
    PowerAggregationJob.isOn = false;
    return result;
  }

  public float getChargedWt() throws Exception {
    return chargedWt;
  }

  public String getDevices() throws Exception {
    return eweLinkLegacy.getDevices();
  }

  public String getDeviceStatus() throws Exception {
    return getDevice().getParams().toString();
  }

  public String getPower(boolean newApi) throws Exception {
    if (newApiForGetPower || newApi) {
      return eweLink.devices().getDevice(deviceId).join().getParams().getPower();
    } else {
      return getDevice().getParams().getPower();
    }
  }


  private DeviceItem getDevice() throws Exception {
    DeviceItem device;
    try {
      device = eweLinkLegacy.getDevice(deviceId);
    } catch (Exception e) {
      // todo implement reliable login
      eweLinkLegacy.login();
      device = eweLinkLegacy.getDevice(deviceId);
    }

    return device;
  }

  public String getDeviceStatusNewAPI() throws JsonProcessingException {
    Device device = eweLink.devices().getDevice(deviceId).join();
    return new ObjectMapper().writeValueAsString(device);
  }
}
