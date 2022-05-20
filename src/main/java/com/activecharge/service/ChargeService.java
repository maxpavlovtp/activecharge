package com.activecharge.service;

import com.activecharge.ewelink.api.EweLink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public void onOff() throws Exception {
        // todo configure connection pool
        EweLink eweLink = new EweLink(region, email, password, 60);
        eweLink.login();

        eweLink.setDeviceStatus(deviceId, "on");
        Thread.sleep(4000);
        eweLink.setDeviceStatus(deviceId, "off");
    }

    public String getDevices() throws Exception {
        EweLink eweLink = new EweLink(region, email, password, 60);
        eweLink.login();

        return eweLink.getDevices();
    }

    public String getPower() throws Exception {
        EweLink eweLink = new EweLink(region, email, password, 60);
        eweLink.login();

        return eweLink.getDevice(deviceId).getParams().getPower();
    }
}
