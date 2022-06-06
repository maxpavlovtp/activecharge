package com.km220.ewelink;

import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_POST;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_STATUS_OK;

import com.km220.ewelink.internal.CredentialsResponse;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.utils.SecurityUtils;
import com.km220.ewelink.internal.ws.DispatchRequest;
import com.km220.ewelink.internal.ws.DispatchResponse;
import com.km220.ewelink.internal.ws.WssLogin;
import com.km220.ewelink.model.device.WssResponse;
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

abstract class AbstractWSEwelinkApi extends AbstractEwelinkApi {

  private static final String WSS_URI_TEMPLATE = "wss://%s:%s/api/ws";
  static final String DISPATCH_APP_API_URI = "/dispatch/app";

  protected CredentialsResponse credentials;


  protected AbstractWSEwelinkApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, httpClient);
  }

  protected final WebSocket openWebSocket(WSClientListener clientListener) {
    credentials = getCredentials().join();
    long timestamp = Instant.now().getEpochSecond();
    var latch = new CountDownLatch(1);

    var webSocket = apiResourceRequest(HTTP_POST,
        BodyPublishers.ofString(JsonUtils.serialize(DispatchRequest.builder()
            .accept("ws")
            .version(API_VERSION)
            .appid(applicationId)
            .nonce(SecurityUtils.generateNonce())
            .ts(timestamp)
            .build())),
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
            .buildAsync(URI.create(String.format(WSS_URI_TEMPLATE, dispatchResponse.getDomain(),
                    dispatchResponse.getPort())),
                new WebSocketClient(clientListener, latch)
            )).join();

    webSocket = webSocket.sendText(JsonUtils.serialize(
        WssLogin.builder()
            .action("userOnline")
            .version(API_VERSION)
            .ts(timestamp)
            .at(credentials.getAt())
            .userAgent("app")
            .apikey(credentials.getUser().getApikey())
            .appid(applicationId)
            .nonce(SecurityUtils.generateNonce())
            .sequence(timestamp * 1000)
            .build()), true).join();

    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new EwelinkApiException(e);
    }

    return webSocket;
  }

  private static class WebSocketClient implements WebSocket.Listener {

    private List<CharSequence> parts = new ArrayList<>();
    private CompletableFuture<?> accumulatedMessage = new CompletableFuture<>();
    private final WSClientListener clientListener;

    private final CountDownLatch latch;
    private AtomicBoolean init = new AtomicBoolean(false);

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
      if (init.compareAndSet(false, true)) {
        latch.countDown();
      } else {
        clientListener.onMessage(JsonUtils.deserialize(String.join("", parts), WssResponse.class));
      }
    }
  }
}
