package com.km220.service;

import static com.km220.PowerAggregationJob.CHECK_INTERVAL_MILLIS;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DeviceServiceTest {

  long intervalMultipliedMillis = 5 * CHECK_INTERVAL_MILLIS;
  long chargeSeconds = (intervalMultipliedMillis / 1000);
  long sleepInterval = chargeSeconds * 1000 + intervalMultipliedMillis;

  @Autowired
  DeviceService deviceService;

  @AfterEach
  public void teardown() throws Exception {
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
    deviceService.on(chargeSeconds);
    // then
    assertThat(deviceService.isDeviceOn()).isTrue();

    // and when
    Thread.sleep(sleepInterval);

    // then
    assertThat(deviceService.isDeviceOn()).isFalse();

  }
}
