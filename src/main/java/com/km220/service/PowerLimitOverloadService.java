package com.km220.service;

import com.km220.PowerAggregationJob;
import org.springframework.stereotype.Service;

@Service
public class PowerLimitOverloadService {

  public static final int OVERLOAD_LIMIT_TIMER_SECS = 30;

  public boolean isPowerLimitOvelrloaded() {
    return PowerAggregationJob.chargingDurationSecs > OVERLOAD_LIMIT_TIMER_SECS &&
        PowerAggregationJob.chargingWtAverageWtH > DeviceService.POWER_LIMIT_WTH;
  }
}
