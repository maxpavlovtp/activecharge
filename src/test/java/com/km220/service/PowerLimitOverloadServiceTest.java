package com.km220.service;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PowerLimitOverloadServiceTest {

  @Autowired
  DeviceService deviceService;

  @Autowired
  PowerLimitOverloadService powerLimitOverloadService;

  @AfterEach
  public void teardown() throws Exception {
    Thread.sleep(2000);
    deviceService.off();
  }
}