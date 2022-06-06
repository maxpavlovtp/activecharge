package com.km220.ewelink;

import com.km220.ewelink.internal.utils.JsonUtils;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class WSEwelinkDeviceApi extends AbstractWSEwelinkApi {

  private final WebSocket webSocket;

  public WSEwelinkDeviceApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient,
      final WSClientListener wssClientListener) {
    super(parameters, applicationId, applicationSecret, httpClient);
    this.webSocket = openWebSocket(wssClientListener);
  }

  public void getDeviceStatus(String deviceId) {
    var timestamp = Instant.now().getEpochSecond();
    Map<String, Object> payload = Map.of(
        "action", "query",
        "deviceid", deviceId,
        "apiKey", credentials.getUser().getApikey(),
        "userAgent", "app",
        "sequence", timestamp * 1000,
        "ts", timestamp
    );
    webSocket.sendText(JsonUtils.serialize(payload), true).join();
  }
}
