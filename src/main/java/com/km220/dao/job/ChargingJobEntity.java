package com.km220.dao.job;

import com.km220.dao.DatabaseEntity;
import com.km220.dao.station.StationEntity;
import java.time.OffsetDateTime;
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
  public static final String CHARGED_WT_WS = "charged_wt_ws";
  public static final String VOLTAGE = "voltage";

  public static final String REASON = "reason";
  public static final String PERIOD = "period_sec";
  public static final String STOPPED_ON = "stopped_on";

  private long number;
  private ChargingJobState state;
  private float chargingWt;
  private float chargedWt;
  private float chargedWtWs;
  private float voltage;
  private String reason;
  private int periodSec;
  private StationEntity station;
  private OffsetDateTime stoppedOn;
}
