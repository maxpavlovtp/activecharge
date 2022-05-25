package com.km220.service;

import static java.lang.System.currentTimeMillis;

import com.km220.service.ewelink.EweLink;
import com.km220.service.ewelink.model.Status;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OnService {

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

  // todo move to DB
  static float chargedWt;
  static long onTime;
  static long offTime;

  public Status on(int chargeHours) throws Exception {
    chargedWt = 0;
    onTime = currentTimeMillis();
    offTime = onTime + 3600 * 1000 * chargeHours;

    return eweLink.setDeviceStatus(deviceId, "on");
  }

  public float getChargedWt() throws Exception {
    return chargedWt;
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
}
