package com.km220.service.ewelink;

import com.km220.PowerAggregationJob;
import com.km220.service.DeviceCache;
import org.springframework.stereotype.Service;

@Service
public class PowerLimitOverloadService {

  //todo move to db
  public static final int OVERLOAD_LIMIT_TIMER_SECS = 5;
  private static final int POWER_LIMIT_WTH = 1900;

  public boolean isPowerLimitOvelrloaded() {
    return PowerAggregationJob.chargingDurationSecs > OVERLOAD_LIMIT_TIMER_SECS &&
        DeviceCache.chargingWtAverageWtH > POWER_LIMIT_WTH;
  }

  public int getPowerLimit() {
    return POWER_LIMIT_WTH;
  }
}