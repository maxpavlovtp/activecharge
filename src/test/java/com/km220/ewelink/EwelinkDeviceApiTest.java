package com.km220.ewelink;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.ewelink.model.device.Device;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EwelinkDeviceApiTest {

  static final String EMAIL = "maxpavlov.dp@gmail.com";
  static final String PASSWORD = "Nopassword1";
  static final String REGION = "eu";

  static final long DEVICE_ID = 1001323420;

  private EwelinkClient ewelinkClient;

  @BeforeEach
  void setup() {
    ewelinkClient = EwelinkClient.builder()
        .parameters(new EwelinkParameters(REGION, EMAIL, PASSWORD))
        .build();
  }

  @Test
  void getDeviceShouldReturnDevice() throws ExecutionException, InterruptedException {
    EwelinkDeviceApi ewelinkDeviceApi = ewelinkClient.devices();

    Device device = ewelinkDeviceApi.getDevice(DEVICE_ID).get();

    assertNotNull(device);
  }
}