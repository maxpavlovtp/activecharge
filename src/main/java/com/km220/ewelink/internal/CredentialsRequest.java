package com.km220.ewelink.internal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CredentialsRequest {

    String appid;
    String email;
    String phoneNumber;
    String password;
    String ts;
    String version;
    String nonce;
}
