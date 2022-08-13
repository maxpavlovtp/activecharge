package com.km220.service.job;

import com.km220.cache.ChargingJobCache;
import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.job.ChargingJobRepository;
import com.km220.service.device.ConsumptionUpdate;
import com.km220.service.device.DeviceUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeviceUpdateAdapter implements DeviceUpdater {

  private final ChargingJobRepository chargingJobRepository;
  private final ChargingJobCache chargingJobCache;

  public DeviceUpdateAdapter(final ChargingJobRepository chargingJobRepository,
      final ChargingJobCache chargingJobCache) {
    this.chargingJobRepository = chargingJobRepository;
    this.chargingJobCache = chargingJobCache;
  }

  @Override
  public void onConsumption(final ConsumptionUpdate update) {
    log.debug("Received consumption update. device id = {}, data = {}", update.getDeviceId(),
        update.getOneKwh());

    ChargingJobEntity jobEntity = chargingJobRepository.getByDeviceId(update.getDeviceId());
    if (jobEntity != null) {
      jobEntity.setChargedWtWs(update.getOneKwh());
      chargingJobRepository.update(jobEntity);
      chargingJobCache.put(jobEntity.getId().toString(), jobEntity);
      chargingJobCache.put(jobEntity.getStation().getNumber(), jobEntity);
    }
  }
}
