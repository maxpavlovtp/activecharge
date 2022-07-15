package com.km220.service;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.model.ChargingJob;
import java.util.function.Function;

public class ChargingJobConverter implements Function<ChargingJobEntity, ChargingJob> {

  @Override
  public ChargingJob apply(final ChargingJobEntity jobEntity) {
    var job = new ChargingJob(jobEntity.getStation().getNumber(),
        jobEntity.getCreatedOn().toEpochSecond(), jobEntity.getPeriodSec());
    job.setCharginWt(jobEntity.getChargingWt());
    job.setChargedWt(jobEntity.getChargedWt());
    job.setVoltage(jobEntity.getVoltage());
    job.setState(jobEntity.getState());
    if (jobEntity.getStoppedOn() != null) {
      job.setStoppedS(jobEntity.getStoppedOn().toEpochSecond());
    }
    return job;
  }
}
