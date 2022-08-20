package com.km220.schedulingtasks;

import com.km220.config.StationScanProperties;
import com.km220.service.job.ChargerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {

  private final ChargerService chargerService;
  private final StationScanProperties stationScanProperties;

  public ScheduledTasks(final ChargerService chargerService,
      StationScanProperties stationScanProperties) {
    this.chargerService = chargerService;
    this.stationScanProperties = stationScanProperties;
  }

  @Scheduled(fixedDelayString = "${station.scan-delay-ms}", initialDelay = 1000)
  public void scanChargingJobs() {
    chargerService.refreshStatus(stationScanProperties.getScanBatchSize(),
        stationScanProperties.getScanDelayMs());
  }
}
