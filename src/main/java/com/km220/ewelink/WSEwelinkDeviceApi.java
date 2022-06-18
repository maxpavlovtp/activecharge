package com.km220.ewelink;

import com.km220.ewelink.internal.CloseableWSEwelinkApi;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.ws.WssGetDeviceStatus;
import com.km220.ewelink.internal.ws.WssSetDeviceStatus;
import com.km220.ewelink.model.device.Params;
import com.km220.ewelink.model.ws.WssResponse;
import java.net.http.HttpClient;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public final class WSEwelinkDeviceApi extends CloseableWSEwelinkApi {

  public WSEwelinkDeviceApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, httpClient);
  }

  public CompletableFuture<WssResponse> getStatus(String deviceId) {
    var timestamp = Instant.now().getEpochSecond();
    return sendMessageAsync(JsonUtils.serialize(WssGetDeviceStatus.builder()
        .action(QUERY_ACTION)
        .deviceid(deviceId)
        .userAgent(APP_USER_AGENT)
        .sequence(timestamp * 1000)
        .ts(timestamp)
        .build()
    ));
  }

  public CompletableFuture<WssResponse> toggle(String deviceId, boolean state) {
    var timestamp = Instant.now().getEpochSecond();
    Params params = Params.builder()
        ._switch(state ? "on" : "off")
        .build();
    return sendMessageAsync(JsonUtils.serialize(WssSetDeviceStatus.builder()
        .action(UPDATE_ACTION)
        .deviceid(deviceId)
        .userAgent(APP_USER_AGENT)
        .sequence(timestamp * 1000)
        .ts(timestamp)
        .params(params)
        .build()
    ));
  }
}
