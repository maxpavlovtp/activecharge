package com.km220.service;

import static com.km220.PowerAggregationJob.chargedWt;
import static com.km220.PowerAggregationJob.offTime;
import static com.km220.PowerAggregationJob.onTime;
import static com.km220.service.PowerLimitOverloadService.OVERLOAD_LIMIT_TIMER_SECS;
import static java.lang.System.currentTimeMillis;

import com.km220.PowerAggregationJob;
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

  EweLink eweLink;

  @PostConstruct
  public void init() throws Exception {
    eweLink = new EweLink(region, email, password, 60);
    eweLink.login();
  }

  public Status on() throws Exception {
    return on(OVERLOAD_LIMIT_TIMER_SECS);
  }

  public Status on(long chargeSeconds) throws Exception {
    // todo refactor
    PowerAggregationJob.chargeDurationSecs = chargeSeconds;
    PowerAggregationJob.chargedWt = 0;
    PowerAggregationJob.onTime = currentTimeMillis();
    PowerAggregationJob.offTime = onTime + 1000L * PowerAggregationJob.chargeDurationSecs;

    Status status = eweLink.setDeviceStatus(deviceId, "on");
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
      result = eweLink.setDeviceStatus(deviceId, "off");
    } catch (Exception e) {
      // todo fix login hack
      eweLink.login();
      result = eweLink.setDeviceStatus(deviceId, "off");
    }

    System.out.println("Charge has been finished");
    PowerAggregationJob.isOn = false;
    return result;
  }

  public float getChargedWt() throws Exception {
    return chargedWt;
  }

  public String getDevices() throws Exception {
    return eweLink.getDevices();
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
      device = eweLink.getDevice(deviceId);
    } catch (Exception e) {
      // todo implement reliable login
      eweLink.login();
      device = eweLink.getDevice(deviceId);
    }

    return device;
  }
}
