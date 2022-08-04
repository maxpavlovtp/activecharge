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

    wsEwelinkDeviceApi.toggle("10013bb124", SwitchState.ON, 10);

    Thread.sleep(1000);

    wsEwelinkDeviceApi.startConsumption("10013bb124");

    Thread.sleep(1000);

    wsEwelinkDeviceApi.refreshConsumption("10013bb124");

    Thread.sleep(1000);

    wsEwelinkDeviceApi.refreshConsumption("10013bb124");

    wsEwelinkDeviceApi.stopConsumption("10013bb124");

    wsEwelinkDeviceApi.getHistoricalConsumption("10013bb124");

    Thread.sleep(1000);

    wsEwelinkDeviceApi.queryStatus("10013bb124");

    //TODO: validate result
    Thread.sleep(1000);
  }
}