package com.km220.service;

import static com.km220.PowerAggregationJob.chargedWt;
import static com.km220.PowerAggregationJob.offTime;
import static com.km220.PowerAggregationJob.onTime;
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

  public static boolean isOn;

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

  public Status on(long chargeSeconds) throws Exception {
    // todo refactor
    PowerAggregationJob.chargeDurationSecs = chargeSeconds;
    chargedWt = 0;
    onTime = currentTimeMillis();
    offTime = onTime + 1000L * PowerAggregationJob.chargeDurationSecs;
    isOn = true;

    return eweLink.setDeviceStatus(deviceId, "on");
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
    isOn = false;
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
    DeviceItem device = null;
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
