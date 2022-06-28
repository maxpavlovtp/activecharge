package com.km220.ewelink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.ws.WssResponse;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WSEwelinkDeviceApiTest {

  //  todo move to props
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
  void getDeviceStatus_shouldReturnDeviceStatus() {
    WssResponse response = wsEwelinkDeviceApi.getStatus(DRYER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", DRYER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());

    response = wsEwelinkDeviceApi.getStatus(DRYER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", DRYER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());
  }

  @Test
  void toggleDeviceState_shouldChangeDeviceToggleState() {
    WssResponse response = wsEwelinkDeviceApi.toggle(DRYER_DEVICE_ID, SwitchState.ON, 1).join();
    LOGGER.info("Device id = {}. Response = {}.", DRYER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());

    response = wsEwelinkDeviceApi.getStatus(DRYER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", DRYER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());
    assertEquals(SwitchState.ON, response.getParams().getSwitchState());

    response = wsEwelinkDeviceApi.toggle(DRYER_DEVICE_ID, SwitchState.OFF, 1).join();
    LOGGER.info("Device id = {}. Response = {}.", DRYER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());

    response = wsEwelinkDeviceApi.getStatus(DRYER_DEVICE_ID).join();
    LOGGER.info("Device id = {}. Response = {}.", DRYER_DEVICE_ID, response);
    assertNotNull(response);
    assertEquals(0, response.getError());
    assertEquals(SwitchState.OFF, response.getParams().getSwitchState());
  }

  ConcurrentHashMap<String, Integer> errorStat = new ConcurrentHashMap<>();

//  todo improve test to check multiple devices
  @Test
  void getDeviceStatusStressTest() {
//    given
    wsEwelinkDeviceApi.toggle(DRYER_DEVICE_ID, SwitchState.ON, 100).join();
//    when
    (new Thread(() -> ddosEwelink("thread 1 "))).start();
    ddosEwelink("thread 2 ");

    LOGGER.info("Errors stat");
    errorStat.forEach((s, integer) -> LOGGER.info("{} -> {}", s, integer));

    Integer successCalls = errorStat.get("0");
    Integer failedCalls = errorStat.get("406");

    // then
    assertTrue(successCalls > failedCalls / 2);
  }

  private void ddosEwelink(String threadName) {
    for (int i = 0; i < 10; i++) {
      WssResponse response = EwelinkClient.builder()
          .parameters(new EwelinkParameters(REGION, EMAIL, PASSWORD))
          .build()
          .wsDevices().getStatus(DRYER_DEVICE_ID).join();

      LOGGER.info("threadName {} error {} ", threadName, response.getError());

      String key = response.getError().toString();

      errorStat.merge(key, 1, Integer::sum);

//      LOGGER.info("Errors stat");
//      concurrentHashMap.forEach((s, integer) -> LOGGER.info("{} -> {}", s, integer));
    }
  }
}