package com.km220.ewelink.v2;

import static com.km220.ewelink.internal.EwelinkConstants.CONSUMPTION_DATE_FORMAT;
import static com.km220.ewelink.internal.EwelinkConstants.GET_CONSUMPTION;
import static com.km220.ewelink.internal.EwelinkConstants.START_CONSUMPTION;
import static com.km220.ewelink.internal.EwelinkConstants.STOP_CONSUMPTION;

import com.km220.ewelink.CredentialsStorage;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.WSClientListener;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.v2.AbstractWSEwelinkApiV2;
import com.km220.ewelink.model.device.Params;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.ws.v2.WssGetDeviceStatus;
import com.km220.ewelink.model.ws.v2.WssSetDeviceStatus;
import java.net.http.HttpClient;
import java.time.Instant;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class WSEwelinkDeviceApiV2 extends AbstractWSEwelinkApiV2 {

  public WSEwelinkDeviceApiV2(final EwelinkParameters parameters,
      final String applicationId,
      final String applicationSecret,
      final CredentialsStorage credentialsStorage,
      final WSClientListener wsClientListener,
      final HttpClient httpClient,
      final int httpRequestTimeoutSec) {
    super(parameters, applicationId, applicationSecret, credentialsStorage, wsClientListener,
        httpClient, httpRequestTimeoutSec);
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
        .oneKwh(START_CONSUMPTION)
        .startTime(CONSUMPTION_DATE_FORMAT.format(Date.from(Instant.now())))
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  public void startConsumption(String deviceId) {
    log.debug("Start device consumption. Device id = {}", deviceId);
    var params = Params.builder()
        .oneKwh(START_CONSUMPTION)
        .startTime(CONSUMPTION_DATE_FORMAT.format(Date.from(Instant.now())))
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  public void stopConsumption(String deviceId) {
    log.debug("Stop device consumption. Device id = {}", deviceId);
    var params = Params.builder()
        .oneKwh(STOP_CONSUMPTION)
        .endTime(CONSUMPTION_DATE_FORMAT.format(Date.from(Instant.now())))
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  public void refreshConsumption(String deviceId) {
    log.debug("Query device consumption. Device id = {}", deviceId);
    var params = Params.builder()
        .oneKwh(GET_CONSUMPTION)
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }

  public void getHistoricalConsumption(String deviceId) {
    log.debug("Refresh historical consumption. Device id = {}", deviceId);
    var params = Params.builder()
        .hundredDaysKwh(GET_CONSUMPTION)
        .build();
    var message = JsonUtils.serialize(WssSetDeviceStatus.create(deviceId, params));
    sendMessage(message);
  }
}
