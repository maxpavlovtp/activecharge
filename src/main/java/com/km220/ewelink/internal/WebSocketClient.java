package com.km220.ewelink.internal;

import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.model.ws.Config;
import com.km220.ewelink.model.ws.WssResponse;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketClient implements WebSocket.Listener {

  private List<CharSequence> parts = new ArrayList<>();
  private CompletableFuture<?> accumulatedMessage = new CompletableFuture<>();

  public final AtomicReference<WssResponse> handshakeResponse = new AtomicReference<>();
  private final CountDownLatch latch;
  private final AtomicBoolean init = new AtomicBoolean(false);

  private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

  private final WSClientListener clientListener;
  private final WebSocketHandler webSocketHandler;

  private static final Logger logger = LoggerFactory.getLogger(WebSocketClient.class);

  public WebSocketClient(WSClientListener clientListener,
      final WebSocketHandler webSocketHandler,
      CountDownLatch latch) {
    this.clientListener = clientListener;
    this.webSocketHandler = webSocketHandler;
    this.latch = latch;
  }

  @Override
  public void onOpen(WebSocket webSocket) {
    logger.debug("Socket was opened");
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
    logger.error("Socket error.", error);
    parts = new ArrayList<>();
    clientListener.onError(error);
    stopScheduleHeartBeats();
    webSocketHandler.disconnect();
  }

  @Override
  public CompletionStage<?> onClose(final WebSocket webSocket, final int statusCode,
      final String reason) {
    logger.debug("Socket closing.. ");
    stopScheduleHeartBeats();
    return Listener.super.onClose(webSocket, statusCode, reason);
  }

  private void onMessageCompleted() {
    var body = String.join("", parts);

    logger.debug("Websocket message received: {}", body);

    WssResponse response = JsonUtils.deserialize(body, WssResponse.class);
    if (init.compareAndSet(false, true)) {
      scheduleHeartBeats(response.getConfig());
      handshakeResponse.set(response);
      latch.countDown();
      return;
    }

    clientListener.onMessage(response);
  }

  private void scheduleHeartBeats(Config config) {
    if (config != null && config.getHb() == 1) {
      int intervalInSeconds = config.getHbInterval();
      executorService.scheduleAtFixedRate(() -> {
        logger.trace("Heartbeat sending.. ");
        webSocketHandler.ping();
      }, intervalInSeconds, intervalInSeconds, TimeUnit.SECONDS);
    }
  }

  private void stopScheduleHeartBeats() {
    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException e) {
      executorService.shutdownNow();
    }
  }
}
