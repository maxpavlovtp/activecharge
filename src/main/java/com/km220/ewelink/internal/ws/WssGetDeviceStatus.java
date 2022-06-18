package com.km220.ewelink.internal.ws;

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
  Long sequence;
  Long ts;
  List<String> params;
}
