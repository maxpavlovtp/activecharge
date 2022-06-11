package com.km220.ewelink;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.ewelink.model.ws.WssResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WSEwelinkDeviceApiTest {

  static final String EMAIL = "maxpavlov.dp@gmail.com";
  static final String PASSWORD = "Nopassword1";
  static final String REGION = "eu";

  private static final String DEVICE_ID = "1001323420";

  private EwelinkClient ewelinkClient;

  @BeforeEach
  void setup() {
    ewelinkClient = EwelinkClient.builder()
        .parameters(new EwelinkParameters(REGION, EMAIL, PASSWORD))
        .build();
  }

  @Test
  void getDeviceShouldReturnDevice() throws InterruptedException {
    var api = ewelinkClient.wsDevices();

    for (int i = 0; i < 10000; i++) {
      WssResponse response = api.getDeviceStatus(DEVICE_ID);
      assertNotNull(response);

      System.out.println(response);
      Thread.sleep(1000);
    }
  }
}