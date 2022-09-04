package com.km220.ewelink.v2;

import com.km220.ewelink.AbstractEwelinkApiTest;
import com.km220.ewelink.model.v2.DeviceV2;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
public class EwelinkLimitationsITest extends AbstractEwelinkApiTest {

  @Test
  @Disabled
  void shouldWorkForTwoDevicesWithNoLimits() throws InterruptedException {
    Function<String, Void> runnable = deviceId -> {
      for (int i = 0; i < 20; i++) {
        EwelinkDeviceApiV2 ewelinkDeviceApi = ewelinkClient.devicesV2();
        DeviceV2 device = ewelinkDeviceApi.getStatus(deviceId).join();

        log.info("Thread: {}. Device: {}", Thread.currentThread().getName(), device.getData().getParams().getPower());
      }
      return null;
    };

    Runnable task1 = () -> runnable.apply(LOCAL_2);
    Runnable task2 = () -> runnable.apply(LOCAL_1);

    Thread t1 = new Thread(task1);
    Thread t2 = new Thread(task2);

    t1.start();
    //t2.start();

    t1.join();
    //t2.join();
  }

}
