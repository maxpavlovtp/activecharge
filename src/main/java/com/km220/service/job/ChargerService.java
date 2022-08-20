package com.km220.service.job;

import static java.lang.String.format;
import static java.time.ZoneOffset.UTC;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.job.ChargingJobState;
import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import com.km220.service.device.DeviceService;
import com.km220.utils.ExceptionUtils;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ChargerService {

  private final DeviceService deviceService;
  private final StationRepository stationRepository;
  private final ChargingJobService chargingJobService;

  public ChargerService(final DeviceService deviceService,
      final ChargingJobService chargingJobService,
      final StationRepository stationRepository) {
    this.deviceService = deviceService;
    this.chargingJobService = chargingJobService;
    this.stationRepository = stationRepository;
  }

  @Transactional
  public UUID start(String stationNumber, int periodSeconds) {
    log.info("Starting new charger. Station number = {}, period = {} seconds", stationNumber,
        periodSeconds);

    StationEntity station = stationRepository.getByNumber(stationNumber);
    if (station == null) {
      throw new NoStationException("Station with number = " + stationNumber + " doesn't exist");
    }
    UUID jobId = chargingJobService.create(stationNumber, periodSeconds);

    deviceService.toggleOn(station.getDeviceId(), periodSeconds);
    //TODO: handle failure toggling device on. we need to mark this in DB.
    log.info("Charging task created. Station number = {}, period = {} seconds", stationNumber,
        periodSeconds);

    return jobId;
  }

  @Transactional
  public void refreshStatus(int batchSize, int scanDelayMs) {
    List<ChargingJobEntity> jobs = chargingJobService.scanActive(batchSize, scanDelayMs);
    for (ChargingJobEntity jobEntity : jobs) {
      ExceptionUtils.runSafely(() -> refreshStatus(jobEntity),
          () -> format(Locale.ROOT, "Job failed. id = %s, station number = %s", jobEntity.getId(),
              jobEntity.getStation().getNumber()));
      chargingJobService.update(jobEntity);
    }
  }

  private void refreshStatus(ChargingJobEntity jobEntity) {
    log.info("Process charging job with id = {}, number = {}, station number = {}",
        jobEntity.getId(), jobEntity.getNumber(), jobEntity.getStation().getNumber());

    //request device state (power, voltage, switch)
    deviceService.requestDeviceState(jobEntity.getStation().getDeviceId());
    //request consumption
    deviceService.requestConsumption(jobEntity.getStation().getDeviceId());

    boolean completed = OffsetDateTime.now(UTC)
        .isAfter(jobEntity.getCreatedOn().plusSeconds(jobEntity.getPeriodSec()));
    if (completed) {
      ExceptionUtils.runSafely(() -> Thread.sleep(1000));

      deviceService.toggleOff(jobEntity.getStation().getDeviceId(), jobEntity.getPeriodSec());

      jobEntity.setReason("Completed");
      jobEntity.setState(ChargingJobState.DONE);
      jobEntity.setStoppedOn(OffsetDateTime.now(ZoneOffset.UTC));
    }
  }
}
