package com.km220.ewelink.internal;

import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.model.ws.WssResponse;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class WebSocketClient implements WebSocket.Listener {

  private List<CharSequence> parts = new ArrayList<>();
  private CompletableFuture<?> accumulatedMessage = new CompletableFuture<>();
  private final WSClientListener clientListener;

  public final AtomicReference<WssResponse> handshakeResponse = new AtomicReference<>();

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
