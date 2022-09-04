package com.km220.ewelink.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.ewelink.AbstractEwelinkApiTest;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.v2.DeviceV2;
import java.util.function.Function;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class EwelinkDeviceApiV2Test extends AbstractEwelinkApiTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(EwelinkDeviceApiV2Test.class);

  private final EwelinkDeviceApiV2 ewelinkDeviceApi;

  public EwelinkDeviceApiV2Test() {
    ewelinkDeviceApi = ewelinkClient.devicesV2();
  }

  @Test
  void getDevice_shouldReturnDevice() {
    DeviceV2 device = ewelinkDeviceApi.getStatus(LOCAL_2).join();

    assertNotNull(device);
    assertNotNull(device.getData());
    assertNotNull(device.getData().getParams());
  }

  @Test
  @Disabled
  void getDevice_shouldReturnDevice_inParallelMode() throws InterruptedException {
    Function<String, Void> runnable = deviceId -> {
      for (int i = 0; i < 10; i++) {
        EwelinkDeviceApiV2 ewelinkDeviceApi = ewelinkClient.devicesV2();
        DeviceV2 device = ewelinkDeviceApi.getStatus(LOCAL_2).join();

        LOGGER.info("Thread: {}. Device: {}", Thread.currentThread().getName(), device);
      }
      return null;
    };

    Runnable task1 = () -> runnable.apply(LOCAL_2);
    Runnable task2 = () -> runnable.apply(LOCAL_1);

    Thread t1 = new Thread(task1);
    Thread t2 = new Thread(task2);

    t1.start();
    t2.start();

    t1.join();
    t2.join();
  }

  @Test
  void toggleDeviceState_shouldChangeDeviceToggleState() {
    DeviceV2 response = ewelinkDeviceApi.toggle(LOCAL_2, SwitchState.ON, 1).join();
    LOGGER.info("Device id = {}. Response = {}.", LOCAL_2, response);
    assertNotNull(response);
    assertEquals(0, response.getError());

    response = ewelinkDeviceApi.getStatus(LOCAL_2).join();
    LOGGER.info("Device id = {}. Response = {}.", LOCAL_2, response);
    assertNotNull(response);
    assertEquals(0, response.getError());
    assertEquals(SwitchState.ON, response.getData().getParams().getSwitchState());

    response = ewelinkDeviceApi.toggle(LOCAL_2, SwitchState.OFF, 1).join();
    LOGGER.info("Device id = {}. Response = {}.", LOCAL_2, response);
    assertNotNull(response);
    assertEquals(0, response.getError());

    response = ewelinkDeviceApi.getStatus(LOCAL_2).join();
    LOGGER.info("Device id = {}. Response = {}.", LOCAL_2, response);
    assertNotNull(response);
    assertEquals(0, response.getError());
    assertEquals(SwitchState.OFF, response.getData().getParams().getSwitchState());
  }
}