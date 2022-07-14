package com.km220.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class StationScanJobConfiguration {

  private final StationScanProperties stationScanProperties;

  public StationScanJobConfiguration(final StationScanProperties stationScanProperties) {
    this.stationScanProperties = stationScanProperties;
  }

  @Bean
  public TaskScheduler taskScheduler() {
    var threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    threadPoolTaskScheduler.setPoolSize(stationScanProperties.getScanThreads());
    threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
    return threadPoolTaskScheduler;
  }
}
