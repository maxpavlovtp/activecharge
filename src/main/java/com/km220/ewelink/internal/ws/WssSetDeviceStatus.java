package com.km220.ewelink.internal.ws;

import com.km220.ewelink.model.device.Params;
import java.util.Map;
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
}
