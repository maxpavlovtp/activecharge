package com.km220.ewelink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.ewelink.model.device.Device;
import java.util.function.Function;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EwelinkDeviceApiTest extends AbstractEwelinkApiTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(EwelinkDeviceApiTest.class);

  @Test
  void getDeviceShouldReturnDevice() {
    EwelinkDeviceApi ewelinkDeviceApi = ewelinkClient.devices();

    Device device = ewelinkDeviceApi.getDevice(LOCAL_1).join();

    assertNotNull(device);
    assertEquals(LOCAL_1, device.getDeviceid());

    device = ewelinkDeviceApi.getDevice(LOCAL_1).join();

    assertNotNull(device);
    assertEquals(LOCAL_1, device.getDeviceid());
  }

  @Test
  @Disabled
  void getDeviceShouldReturnDeviceInParallelMode() throws InterruptedException {
    Function<String, Void> runnable = deviceId -> {
      for (int i = 0; i < 20; i++) {
        EwelinkDeviceApi ewelinkDeviceApi = ewelinkClient.devices();
        Device device = ewelinkDeviceApi.getDevice(LOCAL_1).join();
        LOGGER.info("Thread: {}. Device: {}", Thread.currentThread().getName(), device);

        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }

      return null;
    };

    Runnable task1 = () -> runnable.apply(LOCAL_1);
    Runnable task2 = () -> runnable.apply(LOCAL_2);

    Thread t1 = new Thread(task1);
    //Thread t2 = new Thread(task2);

    t1.start();
    //t2.start();

    t1.join();
    //t2.join();
  }
}