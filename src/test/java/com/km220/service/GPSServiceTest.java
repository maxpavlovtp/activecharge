package com.km220.service;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//  todo make it work on local and ci (create dev instead of local)
//@SpringBootTest
class GPSServiceTest {

//  @Autowired
  GPSService gpsService;

  @Test

  @Disabled
  void getUiNightMode() {
    assertFalse(gpsService.getUiNightMode());
  }
}