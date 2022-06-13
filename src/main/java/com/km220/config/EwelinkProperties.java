package com.km220.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "ewelink")
public class EwelinkProperties {

  private final String region;
  private final String email;
  private final String password;
  private final String appId;
  private final String appSecret;

  public EwelinkProperties(final String region, final String email, final String password,
      final String appId,
      final String appSecret) {
    this.region = region;
    this.email = email;
    this.password = password;
    this.appId = appId;
    this.appSecret = appSecret;
  }

  public String getRegion() {
    return region;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getAppId() {
    return appId;
  }

  public String getAppSecret() {
    return appSecret;
  }
}