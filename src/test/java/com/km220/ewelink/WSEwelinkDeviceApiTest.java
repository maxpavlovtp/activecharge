package com.km220.ewelink;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.ewelink.model.ws.WssResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
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
    var response = new AtomicReference<WssResponse>();

    var latch = new CountDownLatch(1);
    WSClientListener listener = new WSClientListener() {

      @Override
      public void onMessage(final WssResponse message) {
        response.set(message);
        latch.countDown();
      }

      @Override
      public void onError(final Throwable error) {
        latch.countDown();
      }
    };

    WSEwelinkDeviceApi api = ewelinkClient.devices(listener);
    api.getDeviceStatus(DEVICE_ID);
    latch.await(5, TimeUnit.SECONDS);

    assertNotNull(response.get());
  }
}