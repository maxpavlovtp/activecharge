package com.km220.service.job;

import com.km220.config.StationScanProperties;
import com.km220.dao.job.ChargingJobEntity;
import com.km220.service.device.update.ConsumptionUpdate;
import com.km220.service.device.update.DeviceStatusUpdate;
import com.km220.service.device.update.DeviceUpdater;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceUpdateAdapter implements DeviceUpdater {

  private final StationScanProperties stationScanProperties;
  private final ChargingJobService chargingJobService;

  public DeviceUpdateAdapter(final ChargingJobService chargingJobService,
      final StationScanProperties stationScanProperties) {
    this.chargingJobService = chargingJobService;
    this.stationScanProperties = stationScanProperties;
  }

  @Override
  public void onConsumption(final ConsumptionUpdate update) {
    log.debug("Received consumption update. device id = {}, data = {}", update.getDeviceId(),
        update.getOneKwh());

    ChargingJobEntity jobEntity = chargingJobService.findActive(update.getDeviceId());
    if (jobEntity != null) {
      jobEntity.setChargedWtWs(update.getOneKwh());

      chargingJobService.update(jobEntity);
    }
  }

  @Override
  public void onStatus(final DeviceStatusUpdate deviceStatusUpdate) {
    log.debug("Received status update. device id = {}, data = {}", deviceStatusUpdate.getDeviceId(),
        deviceStatusUpdate);

    ChargingJobEntity jobEntity = chargingJobService.findActive(deviceStatusUpdate.getDeviceId());
    if (jobEntity != null) {
      var dirtyHack = 2;
      float chargedWt = jobEntity.getChargedWt() +
          deviceStatusUpdate.getPower() * stationScanProperties.getScanIntervalMs() / (3600 * 1000
              * dirtyHack);
      jobEntity.setChargedWt(chargedWt);
      jobEntity.setChargingWt(deviceStatusUpdate.getPower());
      jobEntity.setVoltage(deviceStatusUpdate.getVoltage());

      chargingJobService.update(jobEntity);
    }
  }
}
