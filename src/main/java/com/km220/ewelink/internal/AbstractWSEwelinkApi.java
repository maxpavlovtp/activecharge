package com.km220.ewelink.internal;

import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_POST;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_STATUS_OK;

import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.utils.SecurityUtils;
import com.km220.ewelink.internal.ws.DispatchRequest;
import com.km220.ewelink.internal.ws.DispatchResponse;
import com.km220.ewelink.internal.ws.WssLogin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.WebSocket;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

abstract class AbstractWSEwelinkApi extends AbstractEwelinkApi {

  private static final String WSS_URI_TEMPLATE = "wss://%s:%s/api/ws";
  static final String DISPATCH_APP_API_URI = "/dispatch/app";
  private String apiKey;
  private WebSocket webSocket;


  protected AbstractWSEwelinkApi(EwelinkParameters parameters, String applicationId,
      String applicationSecret, HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, httpClient);
  }

  protected void openWebSocket(WSClientListener clientListener) {
    var credentials = getCredentials().join();
    long timestamp = Instant.now().getEpochSecond();
    var latch = new CountDownLatch(1);

    var webSocketClient = new WebSocketClient(clientListener, latch);

    webSocket = apiResourceRequest(HTTP_POST,
        BodyPublishers.ofString(JsonUtils.serialize(DispatchRequest.builder()
            .accept("ws")
            .version(API_VERSION)
            .appid(applicationId)
            .nonce(SecurityUtils.generateNonce())
            .ts(timestamp)
            .build())
        ),
        DISPATCH_APP_API_URI,
        Map.of(),
        Map.of(),
        credentials.getAt(),
        HTTP_STATUS_OK
    )
        .thenApply(JsonUtils.jsonDataConverter(DispatchResponse.class))
        .thenCompose(dispatchResponse -> HttpClient
            .newHttpClient()
            .newWebSocketBuilder()
            .buildAsync(URI.create(String.format(WSS_URI_TEMPLATE,
                    dispatchResponse.getDomain(),
                    dispatchResponse.getPort())),
                webSocketClient
            )).join();

    sendMessage(JsonUtils.serialize(
        WssLogin.builder()
            .action("userOnline")
            .version(API_VERSION)
            .ts(timestamp)
            .at(credentials.getAt())
            .userAgent("app")
            .appid(applicationId)
            .nonce(SecurityUtils.generateNonce())
            .sequence(timestamp * 1000)
            .build()), credentials.getUser().getApikey());

    try {
      latch.await();
      apiKey = webSocketClient.handshakeResponse.get().getApikey();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  protected final void closeWebSocket() {
    if (webSocket != null) {
      webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "").join();
      webSocket = null;
    }
  }

  final void sendMessage(String message, String apiKey) {
    if (webSocket == null) {
      throw new IllegalStateException("WebSocket is not opened!");
    }
    var messageWithApiKey = JsonUtils.addPropertyToJson(message, "apikey", apiKey);
    webSocket.sendText(messageWithApiKey, true).join();
  }

  final void sendMessage(String message) {
    sendMessage(message, apiKey);
  }

}
