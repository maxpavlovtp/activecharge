package com.km220.ewelink.internal.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CredentialsRequest {

    String appid;
    String email;
    String phoneNumber;
    String password;
    String countryCode;
    Long ts;
    Integer version;
    String nonce;
}
