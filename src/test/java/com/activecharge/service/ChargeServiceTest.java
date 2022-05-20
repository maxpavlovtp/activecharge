package com.activecharge.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ChargeServiceTest {
    @Autowired
    ChargeService chargeService;

    @Test
    void onOff() throws Exception {
        chargeService.onOff();
    }

    @Test
    void getDevices() throws Exception {
        String devices = chargeService.getDevices();

        assertThat(devices).isNotEmpty();
    }
}