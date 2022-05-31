package com.km220.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DeviceServiceTest {

  @Autowired
  DeviceService deviceService;

  @Test
  void getDevicesTest() throws Exception {
    // when
    String devices = deviceService.getDevices();
    System.out.println(devices);

    // then
    assertThat(devices).isNotEmpty();
  }

  @Test
  void getPowerTest() throws Exception {
    // when
    String power = deviceService.getPower();
    System.out.println(power);

    //then
    assertThat(power).isNotEmpty();
  }

  @Test
  void getChargedWtTest() throws Exception {
    // when
    deviceService.on(1);

    Thread.sleep(3000);
    // then
    assertThat(deviceService.getChargedWt() > 0).isTrue();
  }
}