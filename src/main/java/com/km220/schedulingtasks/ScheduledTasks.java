package com.km220.schedulingtasks;

import com.km220.service.ChargingService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

  private final ChargingService chargingService;

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  public ScheduledTasks(final ChargingService chargingService) {
    this.chargingService = chargingService;
  }

  @Scheduled(fixedRateString = "${station.scanIntervalMs}", initialDelay = 1000)
  public void scanChargingJobs() {
    logger.info("Scan charging jobs {}", dateFormat.format(new Date()));

    chargingService.refresh(10);
  }
}
