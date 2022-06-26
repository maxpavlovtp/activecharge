package com.km220.service.ewelink;

import com.km220.ewelink.model.ws.WssResponse;
import com.km220.model.DeviceStatus;
import com.km220.service.DeviceException;
import java.util.Locale;
import java.util.Objects;

public final class DeviceUtils {

  private DeviceUtils() {
  }

  public static DeviceStatus convert(WssResponse wssResponse) {
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

    return DeviceStatus.builder()
        .deviceId(wssResponse.getDeviceid())
        .switchState(params.getSwitchState().isOn())
        .power(Double.parseDouble(params.getPower()))
        .voltage(Double.parseDouble(params.getVoltage()))
        .build();
  }
}
