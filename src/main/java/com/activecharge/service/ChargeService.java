package com.activecharge.service;

import com.github.realzimboguy.ewelink.api.EweLink;
import com.github.realzimboguy.ewelink.api.model.devices.DeviceItem;
import com.github.realzimboguy.ewelink.api.model.devices.Devices;
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

    public void run() throws Exception {
        EweLink eweLink = new EweLink(region, email, password, 60);
        eweLink.login();

//        eweLink.getDevices();

        String deviceId = "1001323420";
        eweLink.setDeviceStatus(deviceId, "on");
        Thread.sleep(4000);
        eweLink.setDeviceStatus(deviceId, "off");
    }
}
