package com.km220.service;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.model.ChargingJob;
import java.time.Instant;
import java.util.function.Function;

public class ChargingJobConverter implements Function<ChargingJobEntity, ChargingJob> {

  public static final Function<ChargingJobEntity, ChargingJob> converter = new ChargingJobConverter();

  @Override
  public ChargingJob apply(final ChargingJobEntity jobEntity) {
    ChargingJob job = new ChargingJob(jobEntity.getStation().getNumber(),
        jobEntity.getCreatedOn().toEpochSecond(), jobEntity.getPeriodSec());
    job.setCharginWt(jobEntity.getChargingWt());
    job.setChargedWt(jobEntity.getChargedWt());
    job.setState(jobEntity.getState());
    job.setDurationS(Instant.now().getEpochSecond() - jobEntity.getCreatedOn().toEpochSecond());
    job.setLeftS(jobEntity.getPeriodSec() - job.getDurationS());
    return job;
  }
}
