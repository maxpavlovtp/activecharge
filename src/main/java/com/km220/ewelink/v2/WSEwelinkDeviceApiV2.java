package com.km220.ewelink.v2;

import com.km220.ewelink.CredentialsStorage;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.v2.AbstractWSEwelinkApiV2;
import com.km220.ewelink.internal.ws.WssGetDeviceStatus;
import com.km220.ewelink.internal.ws.WssSetDeviceStatus;
import com.km220.ewelink.model.device.Params;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.ws.WssResponse;
import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class WSEwelinkDeviceApiV2 extends AbstractWSEwelinkApiV2 {

  public WSEwelinkDeviceApiV2(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final CredentialsStorage credentialsStorage,
      final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, credentialsStorage, httpClient);

    openWebSocket(new WSClientListenerImpl());
  }

  public void queryStatus(String deviceId) {
    String message = JsonUtils.serialize(WssGetDeviceStatus.create(deviceId));
    sendMessage(message);
  }

  public void toggle(String deviceId, SwitchState state, int chargeSeconds) {
    var params = Params.builder()
        .switchState(state)
        .uiActive(chargeSeconds)
        .oneKwh("start")
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  private static class WSClientListenerImpl implements WSClientListener {

    @Override
    public void onMessage(final WssResponse message) {
      log.info("WS V2 response: {}", message);
    }

    @Override
    public void onError(final Throwable error) {
      log.error("WS V2 error.", error);
    }
  }
}
