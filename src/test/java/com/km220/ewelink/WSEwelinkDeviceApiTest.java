package com.km220.ewelink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.ws.WssResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WSEwelinkDeviceApiTest extends AbstractEwelinkApiTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(WSEwelinkDeviceApiTest.class);

  @Test
  @Disabled
  void getDeviceStatus_shouldReturnDeviceStatus() {
    var wsEwelinkDeviceApi = ewelinkClient.wsDevices();

    WssResponse response = wsEwelinkDeviceApi.getStatus(BOILER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", BOILER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());

    response = wsEwelinkDeviceApi.getStatus(BOILER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", BOILER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());
  }

  @Test
  @Disabled
  void toggleDeviceState_shouldChangeDeviceToggleState() {
    var wsEwelinkDeviceApi = ewelinkClient.wsDevices();

    WssResponse response = wsEwelinkDeviceApi.toggle(BOILER_DEVICE_ID, SwitchState.ON, 1).join();
    LOGGER.info("Device id = {}. Response = {}.", BOILER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());

    response = wsEwelinkDeviceApi.getStatus(BOILER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", BOILER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());
    assertEquals(SwitchState.ON, response.getParams().getSwitchState());

    response = wsEwelinkDeviceApi.toggle(BOILER_DEVICE_ID, SwitchState.OFF, 1).join();
    LOGGER.info("Device id = {}. Response = {}.", BOILER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());

    response = wsEwelinkDeviceApi.getStatus(BOILER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", BOILER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());
    assertEquals(SwitchState.OFF, response.getParams().getSwitchState());
  }
}