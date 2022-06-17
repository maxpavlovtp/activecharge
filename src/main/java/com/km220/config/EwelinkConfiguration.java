package com.km220.config;

import com.km220.ewelink.EwelinkClient;
import com.km220.ewelink.EwelinkParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EwelinkConfiguration {

  private final EwelinkProperties ewelinkProperties;

  public EwelinkConfiguration(final EwelinkProperties ewelinkProperties) {
    this.ewelinkProperties = ewelinkProperties;
  }

  @Bean
  EwelinkClient ewelinkClient() {
    log.info("Init new api v1 client with appid: {}", ewelinkProperties.getAppId());
    return EwelinkClient.builder()
        .applicationId(ewelinkProperties.getAppId())
        .applicationSecret(ewelinkProperties.getAppSecret())
        .parameters(
            new EwelinkParameters(ewelinkProperties.getRegion(),
                ewelinkProperties.getEmail(),
                ewelinkProperties.getPassword()
            )
        )
        .build();
  }
}
