package com.km220.service;

import static com.km220.PowerAggregationJob.chargedWt;
import static com.km220.PowerAggregationJob.onTime;
import static com.km220.service.PowerLimitOverloadService.OVERLOAD_LIMIT_TIMER_SECS;
import static java.lang.System.currentTimeMillis;

import com.km220.PowerAggregationJob;
import com.km220.ewelink.EwelinkDeviceApi;
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

  EweLink eweLinkLegacy;
  EwelinkDeviceApi eweLink;

  @PostConstruct
  public void init() throws Exception {
    eweLinkLegacy = new EweLink(region, email, password, 60);
    eweLinkLegacy.login();

//    eweLink = new EwelinkDeviceApi()
  }

  public Status on() throws Exception {
    return on(OVERLOAD_LIMIT_TIMER_SECS + 2);
  }

  public Status on(long chargeSeconds) throws Exception {
    // todo refactor
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

  public String getPower() throws Exception {
    return getDevice().getParams().getPower();
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
}
