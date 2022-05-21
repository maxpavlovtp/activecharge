package com.km220.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OnServiceTest {
    @Autowired
    OnService onService;

    @Test
    void onOff() throws Exception {
        onService.onOff();
    }

    @Test
    void getDevices() throws Exception {
        String devices = onService.getDevices();

        System.out.println(devices);

        assertThat(devices).isNotEmpty();
    }

    @Test
    void getPower() throws Exception {
        String power = onService.getPower();
        System.out.println(power);

        assertThat(power).isNotEmpty();
    }

    @Test
//    @Disabled
    void apiStressTest() throws Exception {
        float chargedWt = 0;
        for (int i = 0; i < 3600 * 8; i++) {
            String power = onService.getPower();
            System.out.println(power);
            System.out.println(i);
            Thread.sleep(1000);

            float powerWt = Float.parseFloat(power);
            chargedWt += powerWt / 3600;

            System.out.println("chargedWt: " + chargedWt);

            if (i % 100 == 0) {
                onService.login();
            }
        }
    }
}