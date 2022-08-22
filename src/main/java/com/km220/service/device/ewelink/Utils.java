package com.km220.service.device.ewelink;

import com.km220.ewelink.model.v2.DeviceV2;
import com.km220.ewelink.model.ws.WssResponse;
import com.km220.service.device.DeviceState;
import com.km220.service.device.DeviceException;
import java.util.Locale;
import java.util.Objects;

class Utils {

  private Utils() {
  }

  public static DeviceState convert(WssResponse wssResponse) {
    Objects.requireNonNull(wssResponse);

    if (wssResponse.getError() > 0) {
      throw new DeviceException(String.format(Locale.ROOT,
          "Error on getting device status. Device id = %s, Error code = %s",
          wssResponse.getDeviceid(), wssResponse.getError()));
    }

    var params = wssResponse.getParams();
    if (params == null) {
      throw new DeviceException(String.format(Locale.ROOT,
          "Error on getting device status. Couldn't read device parameters. Device id = %s",
          wssResponse.getDeviceid()));
    }

    return DeviceState.builder()
        .deviceId(wssResponse.getDeviceid())
        .switchState(params.getSwitchState().isOn())
        .power(Double.parseDouble(params.getPower()))
        .voltage(Double.parseDouble(params.getVoltage()))
        .build();
  }

  public static DeviceState convert(String deviceId, DeviceV2 deviceV2) {
    Objects.requireNonNull(deviceV2);

    if (deviceV2.getError() > 0) {
      throw new DeviceException(String.format(Locale.ROOT,
          "Error on getting device status. Device id = %s, Error code = %s",
          deviceId, deviceV2.getError()));
    }

    var params = deviceV2.getData().getParams();
    if (params == null) {
      throw new DeviceException(String.format(Locale.ROOT,
          "Error on getting device status. Couldn't read device parameters. Device id = %s",
          deviceId));
    }

    return DeviceState.builder()
        .deviceId(deviceId)
        .switchState(params.getSwitchState().isOn())
        .power(Double.parseDouble(params.getPower()))
        .voltage(Double.parseDouble(params.getVoltage()))
        .build();
  }
}
