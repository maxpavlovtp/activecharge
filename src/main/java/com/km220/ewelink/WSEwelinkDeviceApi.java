package com.km220.ewelink;

import com.km220.ewelink.internal.utils.JsonUtils;
import java.net.http.HttpClient;
import java.time.Instant;
import lombok.Builder;
import lombok.Value;

public final class WSEwelinkDeviceApi extends AbstractWSEwelinkApi {

  public WSEwelinkDeviceApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient,
      final WSClientListener wssClientListener) {
    super(parameters, applicationId, applicationSecret, httpClient, wssClientListener);
  }

  public void getDeviceStatus(String deviceId) {
    var timestamp = Instant.now().getEpochSecond();
    sendText(
        JsonUtils.serialize(WSGetDeviceStatusPayload.builder()
            .action("query")
            .deviceid(deviceId)
            .apikey(getApiKey())
            .userAgent("app")
            .sequence(timestamp * 1000)
            .ts(timestamp)
            .build()
        )
    );
  }

  @Value
  @Builder
  private static class WSGetDeviceStatusPayload {

    String action;
    String deviceid;
    String apikey;
    String userAgent;
    Long sequence;
    Long ts;
  }
}
