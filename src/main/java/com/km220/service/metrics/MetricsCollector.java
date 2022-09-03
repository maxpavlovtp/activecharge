package com.km220.service.metrics;

import com.km220.dao.job.ChargingJobEntity;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class MetricsCollector {

  private final MeterRegistry meterRegistry;

  private final Map<UUID, ChargingJobEntity> jobStatuses = new HashMap<>();

  public MetricsCollector(final MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void onJobUpdate(ChargingJobEntity chargingJobEntity) {
    if (!jobStatuses.containsKey(chargingJobEntity.getId())) {
      var tags = Tags.of(
          "job_id", chargingJobEntity.getId().toString(),
          "device_id", chargingJobEntity.getStation().getDeviceId(),
          "station_number", chargingJobEntity.getStation().getNumber()
      );

      Gauge.builder("job_power_joules", jobStatuses,
              map -> map.get(chargingJobEntity.getId()).getPowerWt())
          .tags(tags)
          .register(meterRegistry);
      Gauge.builder("job_voltage_volts", jobStatuses,
              map -> map.get(chargingJobEntity.getId()).getVoltage())
          .tags(tags)
          .register(meterRegistry);
      Gauge.builder("job_consumption_joules", jobStatuses,
              map -> map.get(chargingJobEntity.getId()).getChargedWtH())
          .tags(tags)
          .register(meterRegistry);
    }

    jobStatuses.put(chargingJobEntity.getId(), chargingJobEntity);
  }

}
