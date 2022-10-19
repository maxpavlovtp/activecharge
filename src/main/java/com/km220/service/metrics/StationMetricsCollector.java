package com.km220.service.metrics;

import com.km220.dao.station.StationEntity;
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
public class StationMetricsCollector {

  private final MeterRegistry meterRegistry;

  private static final String DEVICE_STATE = "device_online_offline";

  private final Map<String, Meter> stateMetrics = new ConcurrentHashMap<>();
  private final Map<String, Boolean> states = new ConcurrentHashMap<>();

  public StationMetricsCollector(final MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void updateOnlineMetric(StationEntity station,
      boolean isOnline) {
    states.put(station.getDeviceId(), isOnline);

    stateMetrics.computeIfAbsent(station.getDeviceId(), id -> Gauge.builder(DEVICE_STATE,
            () -> Optional.ofNullable(states.get(station.getDeviceId()))
                .map(BooleanUtils::toInteger)
                .orElse(null)
        )
        .tags(Tags.of("device_id", station.getDeviceId(), "station_number", station.getNumber()))
        .register(meterRegistry));
  }


}
