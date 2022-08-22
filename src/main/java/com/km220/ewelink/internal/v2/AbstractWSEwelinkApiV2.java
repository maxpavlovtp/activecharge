package com.km220.ewelink.internal.v2;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.km220.ewelink.CredentialsStorage;
import com.km220.ewelink.CredentialsStorage.EwelinkCredentials;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.internal.WebSocketClient;
import com.km220.ewelink.internal.WebSocketHandler;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.utils.SecurityUtils;
import com.km220.ewelink.internal.ws.DispatchResponse;
import com.km220.ewelink.internal.ws.WssLogin;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWSEwelinkApiV2 extends AbstractEwelinkApiV2 implements AutoCloseable {

  private static final String WSS_URI_TEMPLATE = "wss://%s:%s/api/ws";
  private static final String DISPATCH_APP_API_URL = "https://eu-dispa.coolkit.cc/dispatch/app";

  private final WSClientListener clientListener;

  private WebSocket webSocket;
  private String apiSessionKey;

  private static final Logger logger = LoggerFactory.getLogger(AbstractWSEwelinkApiV2.class);

  protected AbstractWSEwelinkApiV2(final EwelinkParameters parameters,
      final String applicationId,
      final String applicationSecret,
      final CredentialsStorage credentialsStorage,
      WSClientListener clientListener,
      final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, credentialsStorage, httpClient);

    this.clientListener = clientListener;

    openWebSocket(clientListener);
  }

  private void openWebSocket(WSClientListener clientListener) {
    logger.info("Open websocket.. ");

    var latch = new CountDownLatch(1);

    //TODO: might not be working on multiple nodes
    login();

    var dispatchResponse = apiGetObjectRequest(DISPATCH_APP_API_URL,
        Map.of(),
        JsonUtils.jsonDataConverter(DispatchResponse.class)
    ).join();

    var webSocketClient = new WebSocketClient(clientListener, new WebSocketHandlerImpl(), latch);

    webSocket = HttpClient.newHttpClient()
        .newWebSocketBuilder()
        .buildAsync(
            URI.create(String.format(WSS_URI_TEMPLATE, dispatchResponse.getDomain(),
                dispatchResponse.getPort())),
            webSocketClient)
        .join();

    logger.info("Start websocket handshake.. ");

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

    logger.info("Connection is ready.");
  }

  private void closeWebSocket() {
    logger.info("Closing websocket.. ");

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
    logger.debug("Sending websocket msg: {}", messageWithApiKey);
    webSocket.sendText(messageWithApiKey, true).join();
  }

  protected void sendMessage(String message) {
    sendMessage(message, apiSessionKey);
  }

  @Override
  public void close() {
    closeWebSocket();
  }

  private class WebSocketHandlerImpl implements WebSocketHandler {
    @Override
    public void ping() {
      var payload = ByteBuffer.wrap("ping".getBytes(UTF_8));
      webSocket.sendPing(payload);
    }

    @Override
    public void onDisconnect() {
      logger.warn("Trying to reestablish websocket connection...");
      openWebSocket(clientListener);
    }
  }
}
