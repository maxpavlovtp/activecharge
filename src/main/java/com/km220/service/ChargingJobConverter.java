package com.km220.service;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.job.ChargingJobState;
import com.km220.model.ChargingJob;
import java.time.Instant;
import java.util.function.Function;

public class ChargingJobConverter implements Function<ChargingJobEntity, ChargingJob> {

  public static final Function<ChargingJobEntity, ChargingJob> converter = new ChargingJobConverter();

  @Override
  public ChargingJob apply(final ChargingJobEntity jobEntity) {
    var job = new ChargingJob(jobEntity.getStation().getNumber(),
        jobEntity.getCreatedOn().toEpochSecond(), jobEntity.getPeriodSec());
    job.setCharginWt(jobEntity.getChargingWt());
    job.setChargedWt(jobEntity.getChargedWt());
    job.setState(jobEntity.getState());
    if (jobEntity.getState() != ChargingJobState.IN_PROGRESS) {
      job.setStoppedS(Instant.now().getEpochSecond());
    }
    return job;
  }
}
