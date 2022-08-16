package com.km220.service.device.ewelink;

import com.km220.ewelink.EwelinkClient;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.v2.DeviceV2;
import com.km220.ewelink.v2.WSEwelinkDeviceApiV2;
import com.km220.service.device.DeviceException;
import com.km220.service.device.DeviceService;
import com.km220.service.device.DeviceState;
import com.km220.service.device.DeviceUpdater;
import java.util.Locale;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EwelinkDeviceService implements DeviceService {

  private final EwelinkClient ewelinkClient;
  private final WSEwelinkDeviceApiV2 wsEwelinkDeviceApiV2;

  public EwelinkDeviceService(final EwelinkClient ewelinkClient, DeviceUpdater deviceUpdater) {
    this.ewelinkClient = ewelinkClient;

    var listener = new WebSocketListenerImpl(deviceUpdater);
    this.wsEwelinkDeviceApiV2 = ewelinkClient.wsDevicesV2(listener);
  }

  @Override
  public DeviceState getState(String deviceId) {
    return ewelinkClient.devicesV2()
        .getStatus(Objects.requireNonNull(deviceId))
        .thenApply(response -> DeviceUtils.convert(deviceId, response))
        .join();
  }

  @Override
  public void toggleOn(String deviceId, int chargeTimeSec) {
    DeviceV2 response = ewelinkClient.devicesV2()
        .toggle(Objects.requireNonNull(deviceId), SwitchState.ON, chargeTimeSec)
        .join();
    if (response.getError() > 0) {
      throw new DeviceException(
          String.format(Locale.ROOT, "Error on switching device. Device id = %s", deviceId));
    }
  }

  @Override
  public void toggleOff(String deviceId, int chargeTimeSec) {
    DeviceV2 response = ewelinkClient.devicesV2()
        .toggle(Objects.requireNonNull(deviceId), SwitchState.OFF, chargeTimeSec)
        .join();
    if (response.getError() > 0) {
      throw new DeviceException(
          String.format(Locale.ROOT, "Error on switching device. Device id = %s", deviceId));
    }
  }

  @Override
  public void requestConsumption(final String deviceId) {
    wsEwelinkDeviceApiV2.refreshConsumption(deviceId);
  }

}
