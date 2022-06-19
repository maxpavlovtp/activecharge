package com.km220.ewelink;

import com.km220.ewelink.internal.CloseableWSEwelinkApi;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.ws.WssGetDeviceStatus;
import com.km220.ewelink.internal.ws.WssSetDeviceStatus;
import com.km220.ewelink.model.device.Params;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.ws.WssResponse;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

public final class WSEwelinkDeviceApi extends CloseableWSEwelinkApi {

  public WSEwelinkDeviceApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, httpClient);
  }

  public CompletableFuture<WssResponse> getStatus(String deviceId) {
    return sendMessageAsync(JsonUtils.serialize(WssGetDeviceStatus.create(deviceId)));
  }

  public CompletableFuture<WssResponse> toggle(String deviceId, SwitchState state) {
    var params = Params.builder()
        .switchState(state)
        .build();
    return sendMessageAsync(JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params)));
  }
}
