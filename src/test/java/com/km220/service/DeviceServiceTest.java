package com.km220.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.km220.PowerAggregationJob.checkIntervalInMillis;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DeviceServiceTest {

  @Autowired
  DeviceService deviceService;

  @BeforeEach
  public void before() throws InterruptedException {
    // todo remove after login fix
    System.out.println("Wait a little bit");
    Thread.sleep(5000);
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
    String power = deviceService.getPower();
    System.out.println(power);

    //then
    assertThat(power).isNotEmpty();
  }

  @Test
  void getChargedWtTest() throws Exception {
    // given
    long chargeSeconds = 2 * checkIntervalInMillis / 1000;
    deviceService.on(chargeSeconds);

    // when
    Thread.sleep(chargeSeconds);

    // then
    assertThat(deviceService.getChargedWt() > 0).isTrue();
  }

  @Test
  void onTest() throws Exception {
    // when
    deviceService.on(1);

    // then
    assertThat(deviceService.isDeviceOn()).isTrue();
  }

  @Test
  void offTest() throws Exception {
    // given
    long chargeSecs = 10;
    deviceService.on(chargeSecs);

    // when
    Thread.sleep(chargeSecs * 1000 + checkIntervalInMillis * 2);

    // then
    assertThat(deviceService.isDeviceOn()).isFalse();
  }
}