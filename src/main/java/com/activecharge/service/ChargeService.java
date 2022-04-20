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
        String deviceId = "ab30000079";
        EweLink eweLink = new EweLink(region, email, password, 60);
        eweLink.login();

        Devices getDevices = eweLink.getDevices();

        for (DeviceItem devicelist : getDevices.getDevicelist()) {
            System.out.println(devicelist.getDeviceid());
            System.out.println(devicelist.getName());

            eweLink.getDeviceStatus(devicelist.getDeviceid());

        }


        for (int i = 0; i < 10; i++) {
            System.out.println(eweLink.getDevice(deviceId).getParams().getPower().toString());
            Thread.sleep(1000);
        }
    }
}
