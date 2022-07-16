package com.km220.service;

import com.km220.cache.ChargingJobCache;
import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.job.ChargingJobRepository;
import com.km220.dao.job.ChargingJobState;
import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import com.km220.model.ChargingJob;
import com.km220.service.device.DeviceService;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ChargingService {

  private final ChargingJobRepository chargingJobRepository;
  private final StationRepository stationRepository;
  private final DeviceService deviceService;
  private final ChargingJobCache chargingJobCache;

  private final ChargingJobRunner jobRunner;

  private static final ChargingJobConverter jobConverter = new ChargingJobConverter();

  private static final Logger logger = LoggerFactory.getLogger(ChargingService.class);

  public ChargingService(final ChargingJobRepository chargingJobRepository,
      final StationRepository stationRepository,
      final ChargingJobCache chargingJobCache,
      final DeviceService deviceService) {
    this.stationRepository = stationRepository;
    this.chargingJobRepository = chargingJobRepository;
    this.deviceService = deviceService;
    this.chargingJobCache = chargingJobCache;

    this.jobRunner = new ChargingJobRunner(deviceService);
  }

  public UUID start(String stationNumber, int periodSeconds) {
    logger.info("Starting new charging. Station number = {}, period = {} seconds", stationNumber,
        periodSeconds);

    StationEntity station = stationRepository.getByNumber(stationNumber);
    UUID jobId = chargingJobRepository.add(stationNumber, periodSeconds);
    deviceService.toggleOn(station.getDeviceId(), periodSeconds);

    //TODO: handle failure toggling device on. we need to mark this in DB.

    logger.info("Charging task created. Station number = {}, period = {} seconds", stationNumber,
        periodSeconds);

    return jobId;
  }

  public void refresh(int batchSize, int delayTime) {
    List<ChargingJobEntity> jobs = chargingJobRepository.scan(ChargingJobState.IN_PROGRESS,
        batchSize, delayTime);

    logger.debug("Processing {} jobs..", jobs.size());

    for (ChargingJobEntity job : jobs) {
      try {
        jobRunner.run(job);
        chargingJobRepository.update(job);
        chargingJobCache.put(job.getId(), job);
      } catch (Exception e) {
        logger.error("Failing charging charging", e);
      }
    }
  }

  public ChargingJob get(UUID key) {
    return jobConverter.apply(chargingJobCache.get(key));
  }
}
