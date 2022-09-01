package com.km220.controller.converters;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.model.ChargingJob;
import java.util.function.Function;

public final class ChargingJobConverter implements Function<ChargingJobEntity, ChargingJob>  {

  public static final ChargingJobConverter INSTANCE = new ChargingJobConverter();

  @Override
  public ChargingJob apply(final ChargingJobEntity jobEntity) {
    if (jobEntity == null) {
      return null;
    }

    var job = new ChargingJob(jobEntity.getStation().getNumber(),
        jobEntity.getCreatedOn().toEpochSecond(), jobEntity.getPeriodSec());
    job.setPowerWt(jobEntity.getPowerWt());
    job.setChargedWtH(jobEntity.getChargedWtH());
    job.setVoltage(jobEntity.getVoltage());
    job.setState(jobEntity.getState());
    if (jobEntity.getStoppedOn() != null) {
      job.setStoppedS(jobEntity.getStoppedOn().toEpochSecond());
    }

    return job;
  }
}
