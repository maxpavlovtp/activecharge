package com.km220.service;

import static com.km220.PowerAggregationJob.CHECK_INTERVAL_MILLIS;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DeviceServiceTest {


  @Autowired
  DeviceService deviceService;

  @AfterEach
  public void teardown() throws Exception {
    Thread.sleep(2000);
    deviceService.off();
  }

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
    String power = deviceService.getPower(false);
    System.out.println(power);

    //then
    assertThat(power).isNotEmpty();
  }

  @Test
  void onOffTest() throws Exception {
    // when
    deviceService.on(CHECK_INTERVAL_MILLIS / 1000 * 5);
    // then
    Thread.sleep(CHECK_INTERVAL_MILLIS * 2);
    assertThat(deviceService.isDeviceOn()).isTrue();

    // and when
    Thread.sleep(CHECK_INTERVAL_MILLIS * 5);

    // then
    assertThat(deviceService.isDeviceOn()).isFalse();
  }
}
