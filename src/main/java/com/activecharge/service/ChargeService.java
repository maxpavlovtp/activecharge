package com.activecharge.service;

import com.github.realzimboguy.ewelink.api.EweLink;
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
        String deviceId = "1001323420";
        EweLink eweLink = new EweLink(region, email, password, 60);
        eweLink.login();


        for (int i = 0; i < 100; i++) {
            System.out.println(eweLink.getDevice(deviceId).getParams().getPower().toString());
            Thread.sleep(1000);
        }
    }
}
