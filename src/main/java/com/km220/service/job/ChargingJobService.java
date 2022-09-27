package com.km220.service.job;

import static com.km220.dao.job.ChargingJobState.IN_PROGRESS;
import static java.lang.String.format;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.job.ChargingJobRepository;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ChargingJobService {

  private final ChargingJobRepository chargingJobRepository;

  public ChargingJobService(final ChargingJobRepository chargingJobRepository) {
    this.chargingJobRepository = chargingJobRepository;
  }

  public UUID create(String stationNumber, int periodInSeconds) {
    try {
      return chargingJobRepository.add(stationNumber, periodInSeconds);
    } catch (DuplicateKeyException exception) {
      throw new DuplicateChargingException(format(Locale.ROOT,
          "Duplicate charging. Station number = %s", stationNumber));
    }
  }

  public void update(ChargingJobEntity jobEntity) {
    chargingJobRepository.update(jobEntity);
  }

  public List<ChargingJobEntity> scanActive(int batchSize, int intervalTimeMs) {
    return chargingJobRepository.scan(IN_PROGRESS, batchSize, intervalTimeMs);
  }

  public ChargingJobEntity findByJobId(String jobId) {
    return chargingJobRepository.getById(UUID.fromString(jobId));
  }

  public ChargingJobEntity findByStationNumber(String stationNumber) {
    return chargingJobRepository.getByStationNumber(stationNumber);
  }

  public ChargingJobEntity findActive(String deviceId) {
    return chargingJobRepository.getActiveByDeviceId(deviceId);
  }
}
