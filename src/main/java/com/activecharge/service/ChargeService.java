package com.activecharge.service;

import com.activecharge.ewelink.api.EweLink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ChargeService {

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
        eweLink = new EweLink(region, email, password, 60*8);
        login();
    }

    public void login() throws Exception {
        eweLink.login();
    }

    public void onOff() throws Exception {
        eweLink.setDeviceStatus(deviceId, "on");
        Thread.sleep(4000);
        eweLink.setDeviceStatus(deviceId, "off");
    }

    public String getDevices() throws Exception {
        return eweLink.getDevices();
    }

    public String getPower() throws Exception {
        return eweLink.getDevice(deviceId).getParams().getPower();
    }
}
