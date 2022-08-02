package com.km220.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "station")
@AllArgsConstructor
@Getter
@ToString
public class StationScanProperties {

  private final int scanIntervalMs;
  private final short scanThreads;
  private final int scanDelayMs;
  private final int scanBatchSize;
}
