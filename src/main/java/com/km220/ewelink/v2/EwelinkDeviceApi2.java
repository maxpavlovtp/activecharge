package com.km220.ewelink.v2;

import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.v2.AbstractEwelinkApi2;
import com.km220.ewelink.model.device.Device;
import com.km220.ewelink.model.v2.DeviceV2;
import java.net.http.HttpClient;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EwelinkDeviceApi2 extends AbstractEwelinkApi2 {

  static final String DEVICES_API_URI = "/device/thing/status";

  private static final Logger LOGGER = LoggerFactory.getLogger(EwelinkDeviceApi2.class);

  public EwelinkDeviceApi2(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, httpClient);
  }

  public CompletableFuture<DeviceV2> getDevice(String deviceId) {
    LOGGER.info("Get status device. Device id = {}", deviceId);

    return apiGetObjectRequest(
        DEVICES_API_URI,
        Map.of("id", deviceId,
            "type", "1"
        ),
        JsonUtils.jsonDataConverter(DeviceV2.class)
    );
  }
}
