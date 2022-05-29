package com.km220.service;

import static com.km220.service.OnService.chargedWt;
import static com.km220.service.OnService.onTime;
import static java.lang.System.currentTimeMillis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PowerAggregationJob {

  @Autowired
  private OnService onService;

  final long checkIntervalInMillis = 1000;

  @Scheduled(fixedDelay = checkIntervalInMillis)
  public void sumPower() throws Exception {
    String power = onService.getPower();
    System.out.println("charging power (watts): " + power);
    float chargingTimeSecs = (currentTimeMillis() - onTime) / 1000;
    System.out.println("chargingTimeSecs: " + chargingTimeSecs);

    float powerWt = Float.parseFloat(power);
    chargedWt += powerWt / (3600 * 1000F / checkIntervalInMillis);

    System.out.println("chargedWt: " + chargedWt);
  }
}
