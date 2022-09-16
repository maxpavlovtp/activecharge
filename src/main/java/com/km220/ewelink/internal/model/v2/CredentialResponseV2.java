package com.km220.ewelink.internal.model.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.km220.ewelink.internal.model.CredentialsResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialResponseV2 {
  private int error;
  private String msg;
  private CredentialsResponse data;
}
