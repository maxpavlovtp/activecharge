package com.km220.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StationState {

  private final boolean lastJobPresented;
  private final ChargingJob lastJob;
  private final boolean uiNightMode;

}
