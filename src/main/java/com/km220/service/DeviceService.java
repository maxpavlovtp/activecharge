package com.km220.service;

import com.km220.ewelink.api.EweLink;
import com.km220.ewelink.api.model.Status;
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
    eweLink = new EweLink(region, email, password, 60 * 8);
    eweLink.login();
  }

  public Status on() throws Exception {
    chargedWt = 0;
    return eweLink.setDeviceStatus(deviceId, "on");
  }

  public Status off() throws Exception {
    return eweLink.setDeviceStatus(deviceId, "off");
  }

  public String getDevices() throws Exception {
    return eweLink.getDevices();
  }

  public String getPower() throws Exception {
    try {
      return eweLink.getDevice(deviceId).getParams().getPower();
    } catch (Exception e) {
      eweLink.login();
      return eweLink.getDevice(deviceId).getParams().getPower();
    }
  }

  // todo move to DB
  float chargedWt = 0;

  public float getConsumedPower(int checkIntervalInMilliseconds) throws Exception {
    return 0;
  }
}
