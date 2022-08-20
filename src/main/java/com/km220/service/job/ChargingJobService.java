package com.km220.service.job;

import static com.km220.dao.job.ChargingJobState.IN_PROGRESS;
import static java.lang.String.format;

import com.km220.cache.ChargingJobCache;
import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.job.ChargingJobRepository;
import com.km220.model.ChargingJob;
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
  private final ChargingJobCache chargingJobCache;

  public ChargingJobService(final ChargingJobRepository chargingJobRepository,
      final ChargingJobCache chargingJobCache) {
    this.chargingJobRepository = chargingJobRepository;
    this.chargingJobCache = chargingJobCache;
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
    chargingJobCache.put(jobEntity.getId().toString(), jobEntity);
    chargingJobCache.put(jobEntity.getStation().getNumber(), jobEntity);
  }

  public List<ChargingJobEntity> scanActive(int batchSize, int delayTime) {
    return chargingJobRepository.scan(IN_PROGRESS, batchSize, delayTime);
  }

  public ChargingJobEntity find(String key) {
    return chargingJobCache.get(key);
  }

  public ChargingJobEntity findActive(String deviceId) {
    return chargingJobRepository.getByDeviceId(deviceId);
  }

  public List<ChargingJob> getInProgressJobs() {
    return null;
  }
}
