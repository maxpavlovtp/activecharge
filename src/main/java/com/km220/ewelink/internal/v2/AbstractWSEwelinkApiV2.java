package com.km220.ewelink.internal.v2;

import com.km220.ewelink.CredentialsStorage;
import com.km220.ewelink.CredentialsStorage.EwelinkCredentials;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.internal.WebSocketClient;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.utils.SecurityUtils;
import com.km220.ewelink.internal.ws.DispatchResponse;
import com.km220.ewelink.internal.ws.WssLogin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractWSEwelinkApiV2 extends AbstractEwelinkApiV2 {

  private static final String WSS_URI_TEMPLATE = "wss://%s:%s/api/ws";
  private static final String DISPATCH_APP_API_URL = "https://eu-dispa.coolkit.cc/dispatch/app";

  private WebSocket webSocket;
  private String apiSessionKey;

  protected AbstractWSEwelinkApiV2(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final CredentialsStorage credentialsStorage,
      final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, credentialsStorage, httpClient);
  }

  protected final void openWebSocket(WSClientListener clientListener) {
    var latch = new CountDownLatch(1);

    var webSocketClient = new WebSocketClient(clientListener, latch);

    webSocket = apiGetObjectRequest(DISPATCH_APP_API_URL,
        Map.of(),
        JsonUtils.jsonDataConverter(DispatchResponse.class)
    ).thenCompose(dispatchResponse -> HttpClient
        .newHttpClient()
        .newWebSocketBuilder()
        .buildAsync(
            URI.create(String.format(WSS_URI_TEMPLATE,
                dispatchResponse.getDomain(),
                dispatchResponse.getPort())),
            webSocketClient
        )
    ).join();

    EwelinkCredentials credentials = getCredentials();

    var timestampSeconds = Instant.now().getEpochSecond();
    sendMessage(JsonUtils.serialize(
        WssLogin.builder()
            .action("userOnline")
            .version(8)
            .ts(timestampSeconds)
            .at(credentials.getToken())
            .userAgent("app")
            .appid(applicationId)
            .nonce(SecurityUtils.generateNonce())
            .sequence(timestampSeconds * 1000)
            .build()
    ), credentials.getApiKey());

    try {
      latch.await();
      apiSessionKey = webSocketClient.handshakeResponse.get().getApikey();
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

  private void sendMessage(String message, String apiKey) {
    if (webSocket == null) {
      throw new IllegalStateException("WebSocket is not opened!");
    }
    var messageWithApiKey = JsonUtils.addPropertyToJson(message, "apikey", apiKey);
    webSocket.sendText(messageWithApiKey, true).join();
  }

  protected final void sendMessage(String message) {
    sendMessage(message, apiSessionKey);
  }
}
