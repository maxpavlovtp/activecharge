package com.km220.service;

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
  void getDevicesTest() throws Exception {
    // when
    String devices = onService.getDevices();
    System.out.println(devices);

    // then
    assertThat(devices).isNotEmpty();
  }

  @Test
  void getPowerTest() throws Exception {
    // when
    String power = onService.getPower();
    System.out.println(power);

    //then
    assertThat(power).isNotEmpty();
  }

  @Test
  void getChargedWtTest() throws Exception {
    // when
    onService.on(1);

    Thread.sleep(10000);
    // then
    assertThat(onService.getChargedWt() > 0).isTrue();
  }
}