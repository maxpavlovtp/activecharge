package com.km220.service.device.ewelink;

import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.model.ws.WssResponse;
import com.km220.service.device.ConsumptionUpdate;
import com.km220.service.device.DeviceUpdater;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
class WebSocketListenerImpl implements WSClientListener {

  private final DeviceUpdater deviceUpdater;

  public WebSocketListenerImpl(final DeviceUpdater deviceUpdater) {
    this.deviceUpdater = deviceUpdater;
  }

  @Override
  public void onMessage(final WssResponse message) {
    if (message.getConfig() != null && StringUtils.isNotEmpty(message.getConfig().getOneKwhData())) {
      float oneKwh = Float.parseFloat(message.getConfig().getOneKwhData());
      var consumptionUpdate = ConsumptionUpdate.builder()
          .deviceId(message.getDeviceid())
          .error(message.getError())
          .oneKwh(oneKwh)
          .build();
      deviceUpdater.onConsumption(consumptionUpdate);
    }
  }

  @Override
  public void onError(final Throwable error) {
    log.warn("Ewelink websocket error", error);
  }


}
