package com.km220.ewelink.v2;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.ewelink.AbstractEwelinkApiTest;
import com.km220.ewelink.model.v2.DeviceV2;
import java.util.function.Function;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EwelinkDeviceApi2Test extends AbstractEwelinkApiTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(EwelinkDeviceApi2Test.class);

  @Test
  void getDeviceShouldReturnDevice() {
    EwelinkDeviceApi2 ewelinkDeviceApi = ewelinkClient.devicesV2();

    DeviceV2 device = ewelinkDeviceApi.getDevice(STAGE_DEVICE_ID).join();

    assertNotNull(device);
    assertNotNull(device.getData());
    assertNotNull(device.getData().getParams());

    device = ewelinkDeviceApi.getDevice(STAGE_DEVICE_ID).join();

    assertNotNull(device);
    assertNotNull(device.getData());
    assertNotNull(device.getData().getParams());
  }

  @Test
  @Disabled
  void getDeviceShouldReturnDeviceInParallelMode() throws InterruptedException {
    Function<String, Void> runnable = deviceId -> {
      for (int i = 0; i < 20; i++) {
        EwelinkDeviceApi2 ewelinkDeviceApi = ewelinkClient.devicesV2();
        DeviceV2 device = ewelinkDeviceApi.getDevice(deviceId).join();

        LOGGER.info("Thread: {}. Power: {}", Thread.currentThread().getName(), device.getData().getParams().getPower());
      }
      return null;
    };

    Runnable task1 = () -> runnable.apply(STAGE_DEVICE_ID);
    Runnable task2 = () -> runnable.apply(BOILER_DEVICE_ID);

    Thread t1 = new Thread(task1);
    Thread t2 = new Thread(task2);

    t1.start();
    t2.start();

    t1.join();
    t2.join();
  }
}