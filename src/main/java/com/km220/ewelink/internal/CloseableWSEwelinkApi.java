package com.km220.ewelink.internal;

import com.km220.ewelink.EwelinkApiException;
import com.km220.ewelink.EwelinkClientException;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.model.ws.WssResponse;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class CloseableWSEwelinkApi extends AbstractWSEwelinkApi {

  public CloseableWSEwelinkApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, httpClient);
  }

  protected final CompletableFuture<WssResponse> sendMessageAsync(String message) {
    var listener = new WSClientListenerImpl();
    openWebSocket(listener);
    sendMessage(message);
    return listener.response();
  }

  class WSClientListenerImpl implements WSClientListener {
    private final CountDownLatch latch = new CountDownLatch(1);
    private final AtomicReference<WssResponse> wssResponseRef = new AtomicReference<>();
    private final AtomicReference<Throwable> errorRef  = new AtomicReference<>();

    @Override
    public void onMessage(final WssResponse message) {
      wssResponseRef.set(message);
      closeWebSocket();
      latch.countDown();
    }

    @Override
    public void onError(final Throwable error) {
      errorRef.set(error);
      closeWebSocket();
      latch.countDown();
    }

    CompletableFuture<WssResponse> response() {
      return CompletableFuture.supplyAsync(() -> {
        try {
          if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new EwelinkClientException("Websocket did not response. Timeout = 5 sec.");
          }
          if (errorRef.get() != null) {
            throw new EwelinkClientException(errorRef.get());
          }
          return wssResponseRef.get();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new EwelinkClientException(e);
        } finally {
          closeWebSocket();
        }
      });
    }
  }
}
