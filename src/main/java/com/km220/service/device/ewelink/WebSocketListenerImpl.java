package com.km220.service.device.ewelink;

import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.ws.WssResponse;
import com.km220.service.device.update.ConsumptionUpdate;
import com.km220.service.device.update.DeviceStateUpdate;
import com.km220.service.device.update.DeviceStatusUpdate;
import com.km220.service.device.update.DeviceUpdater;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
class WebSocketListenerImpl implements WSClientListener {

  private final DeviceUpdater deviceUpdater;

  public WebSocketListenerImpl(final DeviceUpdater deviceUpdater) {
    this.deviceUpdater = deviceUpdater;
  }

  //TODO: refactor this method to dispatch websocket pushes in more accurate way
  @Override
  public void onMessage(final WssResponse message) {
    if (message.getError() != null && message.getError() != 0) {
      log.warn("Error received: {}", message);
      return;
    }

    //consumption
    if (message.getConfig() != null && StringUtils.isNotEmpty(
        message.getConfig().getOneKwhData())) {
      var oneKwh = Float.parseFloat(message.getConfig().getOneKwhData());
      var consumptionUpdate = ConsumptionUpdate.builder()
          .deviceId(message.getDeviceid())
          .error(message.getError())
          .oneKwh(oneKwh)
          .build();

      deviceUpdater.onConsumption(consumptionUpdate);
    }

    var params = message.getParams();

    //device status
    if (message.getParams() != null) {
      if (StringUtils.isNotEmpty(params.getPower()) && StringUtils.isNotEmpty(
          params.getVoltage())) {
        var statusUpdate = DeviceStatusUpdate.builder()
            .deviceId(message.getDeviceid())
            .on(params.getSwitchState() == SwitchState.ON)
            .power(Float.parseFloat(params.getPower()))
            .voltage(Float.parseFloat(params.getVoltage()))
            .build();
        deviceUpdater.onStatus(statusUpdate);
      }
    }

    //device online offline
    if (message.getParams() != null && "sysmsg".equals(message.getAction())) {
      deviceUpdater.onState(
          DeviceStateUpdate.builder()
              .deviceId(message.getDeviceid())
              .action(message.getAction())
              .online(message.getParams().getOnline())
              .build()
      );
    }
  }

  @Override
  public void onError(final Throwable error) {
    log.error("Ewelink websocket error", error);
  }


}
