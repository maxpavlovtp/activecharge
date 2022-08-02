package com.km220.config;

import com.km220.dao.ewelink.EwelinkCredentialsRepository;
import com.km220.ewelink.EwelinkClient;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.CredentialsStorage;
import com.km220.service.DatabaseCredentialsStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EwelinkConfiguration {

  private final EwelinkProperties ewelinkProperties;

  public EwelinkConfiguration(final EwelinkProperties ewelinkProperties) {
    this.ewelinkProperties = ewelinkProperties;
  }

  @Bean
  EwelinkClient ewelinkClient(CredentialsStorage credentialsStorage) {
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
        .credentialsStorage(credentialsStorage)
        .build();
  }

  @Bean
  CredentialsStorage tokenStorage(EwelinkCredentialsRepository ewelinkCredentialsRepository) {
    return new DatabaseCredentialsStorage(ewelinkCredentialsRepository);
  }
}
