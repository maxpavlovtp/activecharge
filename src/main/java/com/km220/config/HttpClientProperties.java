package com.km220.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "http-client")
@AllArgsConstructor
@Getter
@ToString
public class HttpClientProperties {

  private final int requestTimeoutSec;
}
