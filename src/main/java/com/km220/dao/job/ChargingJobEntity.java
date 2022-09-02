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

  public static final String POWER_WT = "power_wt";
  public static final String CHARGED_WT_H = "charged_wt_h";
  public static final String VOLTAGE = "voltage";

  public static final String REASON = "reason";
  public static final String PERIOD = "period_sec";
  public static final String STOPPED_ON = "stopped_on";

  private long number;
  private ChargingJobState state;
  private float powerWt;
  private float chargedWtH;
  private float voltage;
  private String reason;
  private int periodSec;
  private StationEntity station;
  private OffsetDateTime stoppedOn;
}
