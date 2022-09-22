package com.km220.controller.converters;

import com.km220.dao.station.StationEntity;
import com.km220.model.Station;
import java.util.function.Function;

public final class StationConverter implements Function<StationEntity, Station> {

  public static final StationConverter INSTANCE = new StationConverter();

  @Override
  public Station apply(final StationEntity stationEntity) {
    if (stationEntity == null) {
      return null;
    }
    return Station.builder()
        .name(stationEntity.getName())
        .deviceId(stationEntity.getDeviceId())
        .number(stationEntity.getNumber())
        .activeJob(ChargingJobConverter.INSTANCE.apply(stationEntity.getActiveJob()))
        .build();
  }
}
