package com.km220.ewelink;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.ewelink.model.ws.WssResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WSEwelinkDeviceApiTest {

  private static final String EMAIL = "jasper.ua@gmail.com";
  private static final String PASSWORD = "12345678";
  private static final String REGION = "eu";
  private static final String DRYER_DEVICE_ID = "1000d61c41";

  private WSEwelinkDeviceApi wsEwelinkDeviceApi;

  private static final Logger LOGGER = LoggerFactory.getLogger(WSEwelinkDeviceApiTest.class);

  @BeforeEach
  void setup() {
    var ewelinkClient = EwelinkClient.builder()
        .parameters(new EwelinkParameters(REGION, EMAIL, PASSWORD))
        .build();
    wsEwelinkDeviceApi = ewelinkClient.wsDevices();
  }

  @Test
  void getDeviceStatus_ShouldReturnDeviceStatus() {
    WssResponse response = wsEwelinkDeviceApi.getDeviceStatus(DRYER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", DRYER_DEVICE_ID, response);
    assertNotNull(response);

    response = wsEwelinkDeviceApi.getDeviceStatus(DRYER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", DRYER_DEVICE_ID, response);
    assertNotNull(response);
  }
}