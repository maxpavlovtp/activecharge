package com.km220.service.metrics;

import com.km220.dao.job.ChargingJobEntity;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class JobMetricsCollector {

  private final MeterRegistry meterRegistry;

  private final Map<UUID, ChargingJobEntity> jobStatuses = new ConcurrentHashMap<>();

  private final Map<UUID, Meter> jobPowerMeters = new ConcurrentHashMap<>();
  private final Map<UUID, Meter> jobVoltageMeters = new ConcurrentHashMap<>();
  private final Map<UUID, Meter> jobConsumptionMeters = new ConcurrentHashMap<>();

  public JobMetricsCollector(final MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void updateJobMetric(ChargingJobEntity chargingJobEntity) {
    var jobId = chargingJobEntity.getId();
    if (!jobStatuses.containsKey(jobId)) {
      var tags = Tags.of(
          "job_id", jobId.toString(),
          "device_id", chargingJobEntity.getStation().getDeviceId(),
          "station_number", chargingJobEntity.getStation().getNumber()
      );

      jobPowerMeters.put(jobId,
          Gauge.builder("job_power_joules", jobStatuses, map -> map.get(jobId).getPowerWt())
              .tags(tags)
              .register(meterRegistry));
      jobVoltageMeters.put(jobId,
          Gauge.builder("job_voltage_volts", jobStatuses, map -> map.get(jobId).getVoltage())
              .tags(tags)
              .register(meterRegistry));
      jobConsumptionMeters.put(jobId,
          Gauge.builder("job_consumption_joules", jobStatuses,
                  map -> map.get(jobId).getChargedWtH())
              .tags(tags)
              .register(meterRegistry));
    }

    jobStatuses.put(chargingJobEntity.getId(), chargingJobEntity);
  }

  public void removeJobMetric(UUID jobId) {
    removeMeter(jobPowerMeters.remove(jobId));
    removeMeter(jobVoltageMeters.remove(jobId));
    removeMeter(jobConsumptionMeters.remove(jobId));
  }

  private void removeMeter(Meter meter) {
    if (meter != null) {
      meterRegistry.remove(meter);
    }
  }

}

