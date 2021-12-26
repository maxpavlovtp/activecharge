package com.activecharge.charger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MainServiceTest {
    @Autowired
    MainService mainService;

    @Test
    void run() throws Exception {
        mainService.run();
    }
}