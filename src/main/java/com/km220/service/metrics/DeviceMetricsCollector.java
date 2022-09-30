package com.km220.service.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

@Component
public class DeviceMetricsCollector {

  private final MeterRegistry meterRegistry;

  private static final String DEVICE_STATE = "device_online_offline";

  private final Map<String, Meter> stateMetrics = new ConcurrentHashMap<>();
  private final Map<String, Boolean> states = new ConcurrentHashMap<>();

  public DeviceMetricsCollector(final MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void updateStateMetric(String deviceId, boolean isOnline) {
    states.put(deviceId, isOnline);

    stateMetrics.computeIfAbsent(deviceId, id -> Gauge.builder(DEVICE_STATE,
            () -> Optional.ofNullable(states.get(deviceId))
                .map(BooleanUtils::toInteger)
                .orElse(0)
        )
        .tags(Tags.of("device_id", deviceId))
        .register(meterRegistry));
  }


}
