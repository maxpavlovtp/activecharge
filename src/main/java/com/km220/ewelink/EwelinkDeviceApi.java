package com.km220.ewelink;

import com.km220.ewelink.model.device.Device;
import java.net.http.HttpClient;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class EwelinkDeviceApi extends AbstractEwelinkApi {

  static final String DEVICES_API_URI = "/user/device/%d";

  public EwelinkDeviceApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, httpClient);
  }

  public CompletableFuture<Device> getDevice(long deviceId) {
    return apiGetObjectRequest(getDevicesApiUri(deviceId),
        Map.of("deviceid", String.valueOf(deviceId)),
        jsonDataConverter(Device.class));
  }

  private static String getDevicesApiUri(long deviceId) {
    return String.format(DEVICES_API_URI, deviceId);
  }
}
