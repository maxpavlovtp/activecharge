package com.activecharge.service;

import com.github.realzimboguy.ewelink.api.EweLink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DiyService {

    @Value("${ewelink.region}")
    private String region;
    @Value("${ewelink.email}")
    private String email;
    @Value("${ewelink.password}")
    private String password;

    public void run() throws Exception {
        String deviceId = "100136f129";
        EweLink eweLink = new EweLink(region, email, password, 60);
        eweLink.login();

        eweLink.setDeviceStatus(deviceId, "on");
        Thread.sleep(1000);
        eweLink.setDeviceStatus(deviceId, "off");

    }
}
