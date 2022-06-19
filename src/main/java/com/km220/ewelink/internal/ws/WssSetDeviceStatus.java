package com.km220.ewelink.internal.ws;

import static com.km220.ewelink.internal.EwelinkConstants.APP_USER_AGENT;
import static com.km220.ewelink.internal.EwelinkConstants.UPDATE_ACTION;

import com.km220.ewelink.model.device.Params;
import java.time.Instant;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class WssSetDeviceStatus {

  String action;
  String deviceid;
  String userAgent;
  Long sequence;
  Long ts;
  Params params;

  public static WssSetDeviceStatus create(String deviceId, Params params) {
    var timestamp = Instant.now().getEpochSecond();
    return WssSetDeviceStatus.builder()
        .action(UPDATE_ACTION)
        .deviceid(deviceId)
        .userAgent(APP_USER_AGENT)
        .sequence(timestamp * 1000)
        .ts(timestamp)
        .params(params)
        .build();
  }
}
