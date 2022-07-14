package com.km220.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "station")
public class StationScanProperties {

  private final long scanIntervalMs;
  private final short scanThreads;

  public StationScanProperties(final long scanIntervalMs, final short scanThreads) {
    this.scanIntervalMs = scanIntervalMs;
    this.scanThreads = scanThreads;
  }

  public long getScanIntervalMs() {
    return scanIntervalMs;
  }

  public short getScanThreads() {
    return scanThreads;
  }
}
