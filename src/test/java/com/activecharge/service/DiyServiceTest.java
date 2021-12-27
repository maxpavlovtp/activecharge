package com.activecharge.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DiyServiceTest {
    @Autowired
    DiyService diYService;

    @Test
    void run() throws Exception {
        diYService.run();
    }
}