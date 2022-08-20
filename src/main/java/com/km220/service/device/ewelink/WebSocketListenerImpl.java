package com.km220.service.device.ewelink;

import static com.km220.ewelink.internal.EwelinkConstants.UPDATE_ACTION;

import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.ws.WssResponse;
import com.km220.service.device.update.ConsumptionUpdate;
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
    if (message.getConfig() != null && StringUtils.isNotEmpty(message.getConfig().getOneKwhData())) {
      var oneKwh = Float.parseFloat(message.getConfig().getOneKwhData());
      var consumptionUpdate = ConsumptionUpdate.builder()
          .deviceId(message.getDeviceid())
          .error(message.getError())
          .oneKwh(oneKwh)
          .build();

      deviceUpdater.onConsumption(consumptionUpdate);
    }

    //device status
    if (UPDATE_ACTION.equals(message.getAction()) && message.getParams() != null) {
      var params = message.getParams();
      if (StringUtils.isEmpty(params.getPower()) || StringUtils.isEmpty(params.getVoltage())) {
        return;
      }
      var statusUpdate = DeviceStatusUpdate.builder()
          .deviceId(message.getDeviceid())
          .on(params.getSwitchState() == SwitchState.ON)
          .power(Float.parseFloat(params.getPower()))
          .voltage(Float.parseFloat(params.getVoltage()))
          .build();
      deviceUpdater.onStatus(statusUpdate);
    }
  }

  @Override
  public void onError(final Throwable error) {
    log.warn("Ewelink websocket error", error);
  }


}
