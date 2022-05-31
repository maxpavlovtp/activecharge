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

  long intervalMultipliedMillis = 3 * checkIntervalInMillis;
  long chargeSeconds = intervalMultipliedMillis / 1000;
  long sleepInterval = chargeSeconds * 1000 + intervalMultipliedMillis;

  @Autowired
  DeviceService deviceService;

  @Test
  public void loginHack() throws Exception {
//    onTest();
    offTest();
//    getPowerTest();
//    getChargedWtTest();

//    getDevicesTest();
  }

  //  @Test
  void getDevicesTest() throws Exception {
    // when
    String devices = deviceService.getDevices();
    System.out.println(devices);

    // then
    assertThat(devices).isNotEmpty();
  }

  //  @Test
  void getPowerTest() throws Exception {
    // when
    String power = deviceService.getPower();
    System.out.println(power);

    //then
    assertThat(power).isNotEmpty();
  }

  //  @Test
  void getChargedWtTest() throws Exception {
    // given
    deviceService.on(chargeSeconds);

    // when
    Thread.sleep(sleepInterval);

    // then
    assertThat(deviceService.getChargedWt() > 0).isTrue();
  }

  //  @Test
  void onTest() throws Exception {
    // given
    deviceService.on(chargeSeconds);

    // when
    Thread.sleep(sleepInterval);

    // then
    assertThat(deviceService.isDeviceOn()).isTrue();
  }

  //  @Test
  void offTest() throws Exception {
    // given
    deviceService.on(chargeSeconds);

    // when
    Thread.sleep(sleepInterval);

    // then
    assertThat(deviceService.isDeviceOn()).isFalse();
  }
}