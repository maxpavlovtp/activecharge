package com.km220.ewelink;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.km220.ewelink.model.ws.WssResponse;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
    BlockingQueue<WssResponse> messageQueue = new LinkedBlockingQueue<>();
    AtomicReference<Throwable> errorRef = new AtomicReference<>();
    WSClientListener listener = new WSClientListener() {

      @Override
      public void onMessage(final WssResponse message) {
        messageQueue.add(message);
      }

      @Override
      public void onError(final Throwable error) {
        error.printStackTrace();
        errorRef.set(error);
      }
    };

    WSEwelinkDeviceApi api = ewelinkClient.devices(listener);

    for (int i = 0; i < 10000; i++) {
      api.getDeviceStatus(DEVICE_ID);

      WssResponse response = messageQueue.poll(5, TimeUnit.SECONDS);
      assertNotNull(response);

      System.out.println(response);

      assertNull(errorRef.get());

      Thread.sleep(1000);
    }
  }
}