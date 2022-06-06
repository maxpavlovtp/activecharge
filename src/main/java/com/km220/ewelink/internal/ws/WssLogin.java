package com.km220.ewelink.internal.ws;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WssLogin {

  String action;
  String at;
  String apikey;
  String appid;
  String nonce;
  Long ts;
  String userAgent;
  Long sequence;
  Integer version;
}
