package com.km220.ewelink.v2;

import static com.km220.ewelink.internal.EwelinkConstants.CONSUMPTION_DATE_FORMAT;
import static com.km220.ewelink.internal.EwelinkConstants.DEVICE_TYPE;
import static com.km220.ewelink.internal.EwelinkConstants.START_CONSUMPTION;
import static com.km220.ewelink.internal.EwelinkConstants.STOP_CONSUMPTION;

import com.km220.ewelink.CredentialsStorage;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.internal.model.v2.DeviceUpdateRequest;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.v2.AbstractEwelinkApiV2;
import com.km220.ewelink.model.device.Params;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.v2.DeviceV2;
import java.net.http.HttpClient;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EwelinkDeviceApiV2 extends AbstractEwelinkApiV2 {

  static final String DEVICES_API_URI = "/device/thing/status";

  private static final Logger logger = LoggerFactory.getLogger(EwelinkDeviceApiV2.class);

  public EwelinkDeviceApiV2(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final CredentialsStorage credentialsStorage,
      final HttpClient httpClient, final int httpRequestTimeoutSec) {
    super(parameters, applicationId, applicationSecret, credentialsStorage, httpClient,
        httpRequestTimeoutSec);
  }

  public CompletableFuture<DeviceV2> getStatus(String deviceId) {
    logger.info("Get status device. Device id = {}", deviceId);

    return apiGetObjectRequest(
        DEVICES_API_URI,
        Map.of("id", deviceId,
            "type", "1"
        ),
        JsonUtils.jsonDataConverter(DeviceV2.class)
    );
  }

  public CompletableFuture<DeviceV2> toggle(String deviceId, SwitchState state, int chargeSeconds) {
    logger.info("Switch device. Device id = {}. State = {}", deviceId, state.getState());

    var params = Params.builder()
        .switchState(state)
        .uiActive(chargeSeconds)
        .oneKwh(state == SwitchState.ON ? START_CONSUMPTION : STOP_CONSUMPTION)
        .startTime(CONSUMPTION_DATE_FORMAT.format(Date.from(Instant.now())))
        .build();
    return apiPostObjectRequest(DEVICES_API_URI,
        Map.of(),
        () -> JsonUtils.serialize(DeviceUpdateRequest.builder()
            .id(deviceId)
            .type(DEVICE_TYPE)
            .params(params)
            .build()
        ),
        JsonUtils.jsonDataConverter(DeviceV2.class)
    );
  }
}
