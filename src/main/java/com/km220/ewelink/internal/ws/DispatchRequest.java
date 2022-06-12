package com.km220.ewelink.internal.ws;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DispatchRequest {

  String appid;
  Long ts;
  Integer version;
  String nonce;
  String accept;
}
