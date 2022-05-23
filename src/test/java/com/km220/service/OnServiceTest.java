package com.km220.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static java.lang.System.currentTimeMillis;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OnServiceTest {

  @Autowired
  OnService onService;

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
  void apiStressTest() throws Exception {
    int checkInterval = 15000;
    float chargedWt = 0;

    long onTime = currentTimeMillis();
    long offTime = onTime + 3600 * 1000 * 8;

    for (int i = 0; offTime > currentTimeMillis(); i++) {
      System.out.println("sleep for ms: " + checkInterval);
      Thread.sleep(checkInterval);

      String power = onService.getPower();
      System.out.println(power);
      System.out.println(i);

      float powerWt = Float.parseFloat(power);
      chargedWt += powerWt / (3600 * 1000F / checkInterval);

      System.out.println("chargedWt: " + chargedWt);
    }
  }
}