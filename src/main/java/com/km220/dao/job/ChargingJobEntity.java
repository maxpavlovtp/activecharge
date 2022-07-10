package com.km220.dao.job;

import com.km220.dao.DatabaseEntity;
import com.km220.dao.station.StationEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public final class ChargingJobEntity extends DatabaseEntity {

  public static final String NUMBER = "number";
  public static final String STATE = "state";
  public static final String STATION_ID = "station_id";
  public static final String CHARGING_WT = "charging_wt";
  public static final String CHARGED_WT = "charged_wt";
  public static final String REASON = "reason";

  long number;
  ChargingJobState state;
  float chargingWt;
  float chargedWt;
  String reason;
  StationEntity station;
}
