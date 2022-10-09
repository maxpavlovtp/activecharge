package com.km220.service.job;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import com.km220.service.device.update.ConsumptionUpdate;
import com.km220.service.device.update.DeviceStateUpdate;
import com.km220.service.device.update.DeviceStatusUpdate;
import com.km220.service.device.update.DeviceUpdater;
import com.km220.service.metrics.StationMetricsCollector;
import com.km220.service.metrics.JobMetricsCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeviceUpdateAdapter implements DeviceUpdater {

  private final ChargingJobService chargingJobService;
  private final StationRepository stationRepository;
  private final JobMetricsCollector jobMetricsCollector;
  private final StationMetricsCollector stationMetricsCollector;

  public DeviceUpdateAdapter(final ChargingJobService chargingJobService,
      final JobMetricsCollector jobMetricsCollector,
      final StationMetricsCollector stationMetricsCollector,
      final StationRepository stationRepository) {
    this.chargingJobService = chargingJobService;
    this.jobMetricsCollector = jobMetricsCollector;
    this.stationMetricsCollector = stationMetricsCollector;
    this.stationRepository = stationRepository;
  }

  @Override
  public void onConsumption(final ConsumptionUpdate update) {
    log.debug("Received consumption update. device id = {}, data = {}", update.getDeviceId(),
        update.getOneKwh());

    ChargingJobEntity jobEntity = chargingJobService.findActive(update.getDeviceId());
    if (jobEntity != null) {
      jobEntity.setChargedWtH(update.getOneKwh() * 1000);
      chargingJobService.update(jobEntity);
      jobMetricsCollector.updateJobMetric(jobEntity);
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
      jobMetricsCollector.updateJobMetric(jobEntity);
    }
  }

  @Override
  public void onState(final DeviceStateUpdate deviceStateUpdate) {
    log.debug("Received state update. device id = {}, is online = {}",
        deviceStateUpdate.getDeviceId(),
        deviceStateUpdate.isOnline());
    StationEntity station = stationRepository.getByDeviceId(deviceStateUpdate.getDeviceId());
    if (station != null) {
      stationMetricsCollector.updateOnlineMetric(station,
          deviceStateUpdate.isOnline());
    }
  }
}
