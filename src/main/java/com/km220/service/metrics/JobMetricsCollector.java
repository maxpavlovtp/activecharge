package com.km220.service.metrics;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.km220.dao.job.ChargingJobEntity;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class JobMetricsCollector {

  private final MeterRegistry meterRegistry;

  private final Map<String, Map<UUID, Meter>> jobMetrics = new ConcurrentHashMap<>();

  private static final String JOB_POWER_JOULES = "job_power_joules";
  private static final String JOB_VOLTAGE_VOLTS = "job_voltage_volts";
  private static final String JOB_CONSUMPTION_JOULES = "job_consumption_joules";

  private final Cache<UUID, ChargingJobEntity> jobs = CacheBuilder.newBuilder()
      .expireAfterWrite(1, TimeUnit.MINUTES)
      .build();

  public JobMetricsCollector(final MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void updateJobMetric(ChargingJobEntity chargingJobEntity) {
    var jobId = chargingJobEntity.getId();

    if (jobs.getIfPresent(jobId) == null) {

      var tags = Tags.of(
          "job_id", jobId.toString(),
          "device_id", chargingJobEntity.getStation().getDeviceId(),
          "station_number", chargingJobEntity.getStation().getNumber()
      );

      addJobMetric(JOB_POWER_JOULES, jobId,
          Gauge.builder(JOB_POWER_JOULES, () -> Optional.ofNullable(jobs.getIfPresent(jobId))
                  .map(ChargingJobEntity::getPowerWt).orElse(null))
              .tags(tags)
              .register(meterRegistry));

      addJobMetric(JOB_VOLTAGE_VOLTS, jobId,
          Gauge.builder(JOB_VOLTAGE_VOLTS, () -> Optional.ofNullable(jobs.getIfPresent(jobId))
                  .map(ChargingJobEntity::getVoltage).orElse(null))
              .tags(tags)
              .register(meterRegistry));

      addJobMetric(JOB_CONSUMPTION_JOULES, jobId,
          Gauge.builder(JOB_CONSUMPTION_JOULES, () -> Optional.ofNullable(jobs.getIfPresent(jobId))
                  .map(ChargingJobEntity::getChargedWtH).orElse(null))
              .tags(tags)
              .register(meterRegistry));
    }

    jobs.put(chargingJobEntity.getId(), chargingJobEntity);
  }

  public void removeJobMetrics(UUID jobId) {
    for (Map<UUID, Meter> meters : jobMetrics.values()) {
      var meter = meters.remove(jobId);
      if (meter != null) {
        meterRegistry.remove(meter);
      }
    }
  }

  private void addJobMetric(String metric, UUID jobId, Meter meter) {
    Map<UUID, Meter> jobMeters = jobMetrics.computeIfAbsent(metric,
        name -> new ConcurrentHashMap<>());
    jobMeters.put(jobId, meter);
  }

}

