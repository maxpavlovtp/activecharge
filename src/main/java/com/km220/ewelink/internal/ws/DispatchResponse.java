package com.km220.ewelink.internal.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatchResponse {

  String port;
  @SerializedName("IP")
  String ip;
  String reason;
  String domain;
  String error;
}
