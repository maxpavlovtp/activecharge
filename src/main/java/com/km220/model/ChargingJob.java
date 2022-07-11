package com.km220.model;

import com.km220.dao.job.ChargingJobState;
import lombok.Data;

@Data
public class ChargingJob {

  private final String stationNumber;
  private final long startedS;
  private final int periodS;
  private ChargingJobState state;
  private double charginWt;
  private double chargedWt;
  private long durationS;
  private long leftS;
}
