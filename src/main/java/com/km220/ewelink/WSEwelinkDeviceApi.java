package com.km220.ewelink;

import com.km220.ewelink.internal.CloseableWSEwelinkApi;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.model.ws.WssResponse;
import java.net.http.HttpClient;
import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Value;

public final class WSEwelinkDeviceApi extends CloseableWSEwelinkApi {

  public WSEwelinkDeviceApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, httpClient);
  }

  public WssResponse getDeviceStatus(String deviceId) {
    var timestamp = Instant.now().getEpochSecond();
   return sendMessageAsync(
        JsonUtils.serialize(WSGetDeviceStatusPayload.builder()
            .action("query")
            .deviceid(deviceId)
            .userAgent("app")
            .sequence(timestamp * 1000)
            .ts(timestamp)
            .build()
        )
    ).join();
  }

  @Value
  @Builder
  private static class WSGetDeviceStatusPayload {

    String action;
    String deviceid;
    String userAgent;
    Long sequence;
    Long ts;
    List<String> params;
  }
}
