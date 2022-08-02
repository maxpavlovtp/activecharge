package com.km220.ewelink.internal.ws;

import static com.km220.ewelink.internal.EwelinkConstants.APP_USER_AGENT;
import static com.km220.ewelink.internal.EwelinkConstants.QUERY_ACTION;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class WssGetDeviceStatus {

  String action;
  String deviceid;
  String userAgent;
  String from;
  Long sequence;
  Long ts;
  List<String> params;

  public static WssGetDeviceStatus create(String deviceId) {
    var timestamp = Instant.now().getEpochSecond();
    return WssGetDeviceStatus.builder()
        .action(QUERY_ACTION)
        .deviceid(deviceId)
        .userAgent(APP_USER_AGENT)
        .from(APP_USER_AGENT)
        .sequence(timestamp * 1000)
        .ts(timestamp)
        .build();
  }
}
