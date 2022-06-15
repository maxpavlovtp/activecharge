package com.km220.service;

import static com.km220.PowerAggregationJob.CHECK_INTERVAL_MILLIS;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
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
    deviceService.off();
  }

  @Test
  @Disabled
  void getDevicesTest() throws Exception {
    // when
    String devices = deviceService.getDevices();
    System.out.println(devices);

    // then
    assertThat(devices).isNotEmpty();
  }

  @Test
  void getPowerTest() throws Exception {
    // given
    deviceService.on(5);

    // when
    Thread.sleep(3000);

    //then
    assertThat(Float.parseFloat(deviceService.getPower(false)) > 0).isTrue();

    // and when
    deviceService.off();
    Thread.sleep(3000);

    assertThat(deviceService.getPower(false)).isEqualTo("0.00");
  }

  @Test
  @Disabled
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
