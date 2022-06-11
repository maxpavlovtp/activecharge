package com.km220.ewelink;

import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_POST;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_STATUS_OK;

import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.utils.SecurityUtils;
import com.km220.ewelink.internal.ws.DispatchRequest;
import com.km220.ewelink.internal.ws.DispatchResponse;
import com.km220.ewelink.internal.ws.WssLogin;
import com.km220.ewelink.model.ws.WssResponse;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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

  private static final class WebSocketClient implements WebSocket.Listener {

    private List<CharSequence> parts = new ArrayList<>();
    private CompletableFuture<?> accumulatedMessage = new CompletableFuture<>();
    private final WSClientListener clientListener;

    private final AtomicReference<WssResponse> handshakeResponse = new AtomicReference<>();

    private final CountDownLatch latch;
    private final AtomicBoolean init = new AtomicBoolean(false);

    public WebSocketClient(WSClientListener clientListener, CountDownLatch latch) {
      this.clientListener = clientListener;
      this.latch = latch;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
      WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data,
        boolean last) {
      parts.add(data);
      webSocket.request(1);
      if (last) {
        onMessageCompleted();
        parts = new ArrayList<>();
        accumulatedMessage.complete(null);
        CompletionStage<?> cf = accumulatedMessage;
        accumulatedMessage = new CompletableFuture<>();
        return cf;
      }
      return accumulatedMessage;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
      parts = new ArrayList<>();
      clientListener.onError(error);
    }

    @Override
    public CompletionStage<?> onClose(final WebSocket webSocket, final int statusCode,
        final String reason) {
      return Listener.super.onClose(webSocket, statusCode, reason);
    }

    private void onMessageCompleted() {
      var body = String.join("", parts);
      WssResponse response = JsonUtils.deserialize(body, WssResponse.class);
      if (init.compareAndSet(false, true)) {
        handshakeResponse.set(response);
        latch.countDown();
        return;
      }
      clientListener.onMessage(response);
    }
  }
}
