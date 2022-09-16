package com.km220.controller.converters;

import com.km220.dao.job.ChargingJobEntity;
import com.km220.model.ChargingJob;
import com.km220.model.StationState;
import java.util.function.Function;

public final class StationStateConverter implements Function<ChargingJobEntity, StationState>  {

  public static final StationStateConverter INSTANCE = new StationStateConverter();

  @Override
  public StationState apply(final ChargingJobEntity jobEntity) {
    if (jobEntity == null) {
      return new StationState(false, null);
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

    return new StationState(true, job);
  }
}
