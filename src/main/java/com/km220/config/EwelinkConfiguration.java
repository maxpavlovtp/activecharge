package com.km220.config;

import com.km220.dao.ewelink.EwelinkTokenRepository;
import com.km220.ewelink.EwelinkClient;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.TokenStorage;
import com.km220.service.TokenStorageImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EwelinkConfiguration {

  private final EwelinkProperties ewelinkProperties;

  public EwelinkConfiguration(final EwelinkProperties ewelinkProperties) {
    this.ewelinkProperties = ewelinkProperties;
  }

  @Bean
  EwelinkClient ewelinkClient(TokenStorage tokenStorage) {
    return EwelinkClient.builder()
        .applicationId(ewelinkProperties.getAppId())
        .applicationSecret(ewelinkProperties.getAppSecret())
        .parameters(
            new EwelinkParameters(
                ewelinkProperties.getRegion(),
                ewelinkProperties.getEmail(),
                ewelinkProperties.getPassword(),
                ewelinkProperties.getCountryCode()
            )
        )
        .tokenStorage(tokenStorage)
        .build();
  }

  @Bean
  TokenStorage tokenStorage(EwelinkTokenRepository ewelinkTokenRepository) {
    return new TokenStorageImpl(ewelinkTokenRepository);
  }
}
