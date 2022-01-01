package com.activecharge.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ChargeServiceTest {
    @Autowired
    ChargeService chargeService;

    @Test
    void run() throws Exception {
        chargeService.run();
    }
}