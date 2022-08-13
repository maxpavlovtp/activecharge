package com.km220.service.job;

import com.km220.cache.ChargingJobCache;
import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.job.ChargingJobRepository;
import com.km220.dao.job.ChargingJobState;
import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import com.km220.model.ChargingJob;
import com.km220.service.device.DeviceService;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
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
    UUID jobId = null;
    try {
      jobId = chargingJobRepository.add(stationNumber, periodSeconds);
    } catch (DuplicateKeyException exception) {
      throw new DuplicateChargingException(String.format(Locale.ROOT,
          "Duplicate charging. Station number = %s", stationNumber));
    }
    deviceService.toggleOn(station.getDeviceId(), periodSeconds);

    //TODO: handle failure toggling device on. we need to mark this in DB.

    logger.info("Charging task created. Station number = {}, period = {} seconds", stationNumber,
        periodSeconds);

    return jobId;
  }

  public void refresh(int batchSize, int scanDelayMs, int scanIntervalMs) {
    List<ChargingJobEntity> jobs = chargingJobRepository.scan(ChargingJobState.IN_PROGRESS,
        batchSize, scanDelayMs);

    for (ChargingJobEntity job : jobs) {
      try {
        jobRunner.run(job, scanIntervalMs);
      } catch (Exception e) {
        logger.error(
            String.format(Locale.ROOT, "Job failed. id = %s, station number = %s",
                job.getId(), job.getStation().getNumber()), e);
      } finally {
        chargingJobRepository.update(job);
        chargingJobCache.put(job.getId().toString(), job);
        chargingJobCache.put(job.getStation().getNumber(), job);
      }
    }
  }

  public ChargingJob get(String key) {
    ChargingJobEntity jobEntity = chargingJobCache.get(key);
    if (jobEntity != null) {
      return jobConverter.apply(jobEntity);
    }
    return null;
  }

  public List<ChargingJob> getInProgressJobs() {
    return null;
  }

  private static class ChargingJobConverter implements Function<ChargingJobEntity, ChargingJob> {

    @Override
    public ChargingJob apply(final ChargingJobEntity jobEntity) {
      var job = new ChargingJob(jobEntity.getStation().getNumber(),
          jobEntity.getCreatedOn().toEpochSecond(), jobEntity.getPeriodSec());
      job.setCharginWt(jobEntity.getChargingWt());
      job.setChargedWt(jobEntity.getChargedWt());
      job.setChargedWtWs(jobEntity.getChargedWtWs());
      job.setVoltage(jobEntity.getVoltage());
      job.setState(jobEntity.getState());
      if (jobEntity.getStoppedOn() != null) {
        job.setStoppedS(jobEntity.getStoppedOn().toEpochSecond());
      }
      return job;
    }
  }
}
