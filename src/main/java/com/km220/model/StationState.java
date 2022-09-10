package com.km220.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StationState {

  private final boolean lastJobPresented;
  private final ChargingJob lastJob;
}
