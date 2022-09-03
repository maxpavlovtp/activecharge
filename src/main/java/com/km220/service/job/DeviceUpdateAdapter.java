package com.km220.service.job;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.service.device.update.ConsumptionUpdate;
import com.km220.service.device.update.DeviceStatusUpdate;
import com.km220.service.device.update.DeviceUpdater;
import com.km220.service.metrics.MetricsCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceUpdateAdapter implements DeviceUpdater {

  private final ChargingJobService chargingJobService;
  private final MetricsCollector metricsCollector;

  public DeviceUpdateAdapter(final ChargingJobService chargingJobService,
      final MetricsCollector metricsCollector) {
    this.chargingJobService = chargingJobService;
    this.metricsCollector = metricsCollector;
  }

  @Override
  public void onConsumption(final ConsumptionUpdate update) {
    log.debug("Received consumption update. device id = {}, data = {}", update.getDeviceId(),
        update.getOneKwh());

    ChargingJobEntity jobEntity = chargingJobService.findActive(update.getDeviceId());
    if (jobEntity != null) {
      jobEntity.setChargedWtH(update.getOneKwh() * 1000);
      chargingJobService.update(jobEntity);
      metricsCollector.onJobUpdate(jobEntity);
    }
  }

  @Override
  public void onStatus(final DeviceStatusUpdate deviceStatusUpdate) {
    log.debug("Received status update. device id = {}, data = {}", deviceStatusUpdate.getDeviceId(),
        deviceStatusUpdate);

    ChargingJobEntity jobEntity = chargingJobService.findActive(deviceStatusUpdate.getDeviceId());
    if (jobEntity != null) {
      jobEntity.setPowerWt(deviceStatusUpdate.getPower());
      jobEntity.setVoltage(deviceStatusUpdate.getVoltage());
      chargingJobService.update(jobEntity);
      metricsCollector.onJobUpdate(jobEntity);
    }
  }
}
