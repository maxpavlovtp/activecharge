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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class WSEwelinkDeviceApiV2 extends AbstractWSEwelinkApiV2 {

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  public WSEwelinkDeviceApiV2(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final CredentialsStorage credentialsStorage,
      final HttpClient httpClient) {
    super(parameters, applicationId, applicationSecret, credentialsStorage, httpClient);

    openWebSocket(new WSClientListenerImpl());
  }

  public void queryStatus(String deviceId) {
    log.debug("Query status device. Device id = {}", deviceId);
    String message = JsonUtils.serialize(WssGetDeviceStatus.create(deviceId));
    sendMessage(message);
  }

  public void toggle(String deviceId, SwitchState state, int chargeSeconds) {
    log.debug("Toggle device ON. Device id = {}. Charge time = {} seconds", deviceId,
        chargeSeconds);
    var params = Params.builder()
        .switchState(state)
        .uiActive(chargeSeconds)
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  public void startConsumption(String deviceId) {
    String startTime = dateFormat.format(Date.from(Instant.now()));
    log.debug("Start device consumption. Device id = {}, Start time = {}", deviceId, startTime);
    var params = Params.builder()
        .oneKwh("start")
        .startTime(startTime)
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  public void stopConsumption(String deviceId) {
    log.debug("Stop device consumption. Device id = {}", deviceId);
    var params = Params.builder()
        .oneKwh("stop")
//        .endTime(dateFormat.format(Date.from(Instant.now())))
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  public void refreshConsumption(String deviceId) {
    log.debug("Refresh consumption. Device id = {}", deviceId);
    var params = Params.builder()
        .oneKwh("get")
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  public void getHistoricalConsumption(String deviceId) {
    log.debug("Refresh historical consumption. Device id = {}", deviceId);
    var params = Params.builder()
        .hundredDaysKwh("get")
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  private static class WSClientListenerImpl implements WSClientListener {

    @Override
    public void onMessage(final WssResponse message) {
      //log.info("WS V2 response: {}", message);
    }

    @Override
    public void onError(final Throwable error) {
      //log.error("WS V2 error.", error);
    }
  }
}
