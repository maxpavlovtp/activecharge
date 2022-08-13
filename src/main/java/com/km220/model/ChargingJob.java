package com.km220.model;

import com.km220.dao.job.ChargingJobState;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class ChargingJob {

  private final String stationNumber;
  private final long startedS;
  private final int periodS;

  private ChargingJobState state;
  private double charginWt;
  private double chargedWt;
  private double chargedWtWs;
  private double voltage;
  private long stoppedS;
  @Getter(AccessLevel.NONE)
  private long durationS;
  @Getter(AccessLevel.NONE)
  private long leftS;

  public long getDurationS() {
    long endS = getStoppedS() != 0 ? getStoppedS() : Instant.now().getEpochSecond();
    return endS - getStartedS();
  }

  public long getLeftS() {
    return Math.max(0, getPeriodS() - getDurationS());
  }
}
