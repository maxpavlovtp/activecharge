package com.km220.ewelink.v2;

import com.km220.ewelink.AbstractEwelinkApiTest;
import com.km220.ewelink.model.device.SwitchState;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class WSEwelinkDeviceApiV2Test extends AbstractEwelinkApiTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(WSEwelinkDeviceApiV2Test.class);

  @Test
  void getDeviceStatus_shouldReturnDeviceStatus() throws InterruptedException {
    var wsEwelinkDeviceApi = ewelinkClient.wsDevicesV2();
    var ewelinkDeviceApi = ewelinkClient.devicesV2();

    //ewelinkDeviceApi.toggle("10013bb124", SwitchState.ON, 5 * 60);
    wsEwelinkDeviceApi.toggle("10013bb124", SwitchState.ON, 5 * 60);
    Thread.sleep(3000 * 60);

    wsEwelinkDeviceApi.refreshConsumption("10013bb124");
    Thread.sleep(1000);

//    wsEwelinkDeviceApi.stopConsumption("10013bb124");
//    Thread.sleep(1000);
//
//    wsEwelinkDeviceApi.refreshConsumption("10013bb124");
//    Thread.sleep(1000);

    //wsEwelinkDeviceApi.toggle("10013bb124", SwitchState.OFF, 5 * 60);
  }
}