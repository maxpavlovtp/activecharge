package com.km220.ewelink.v2;

import com.km220.ewelink.AbstractEwelinkApiTest;
import com.km220.ewelink.model.device.SwitchState;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WSEwelinkDeviceApiV2Test extends AbstractEwelinkApiTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(WSEwelinkDeviceApiV2Test.class);
  public static final String DEVICE_ID = "1001323420";

  @Test
  void getDeviceStatus_shouldReturnDeviceStatus() throws InterruptedException {
    var wsEwelinkDeviceApi = ewelinkClient.wsDevicesV2();

//    wsEwelinkDeviceApi.toggle(DEVICE_ID, SwitchState.ON, 5 * 60);

//    Thread.sleep(1000);

//    wsEwelinkDeviceApi.stopConsumption(DEVICE_ID);

//    Thread.sleep(1000);

//    wsEwelinkDeviceApi.startConsumption(DEVICE_ID);

//    Thread.sleep(5000 * 60);

    //wsEwelinkDeviceApi.stopConsumption("10013bb124");

//    Thread.sleep(1000);

    wsEwelinkDeviceApi.refreshConsumption(DEVICE_ID);

//    Thread.sleep(1000);

    wsEwelinkDeviceApi.getHistoricalConsumption(DEVICE_ID);

    Thread.sleep(1000);

    wsEwelinkDeviceApi.queryStatus(DEVICE_ID);

    //TODO: validate result
//    Thread.sleep(1000);
  }
}