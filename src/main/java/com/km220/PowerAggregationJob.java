package com.km220;

import static com.km220.service.DeviceService.isOn;
import static java.lang.System.currentTimeMillis;

import com.km220.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PowerAggregationJob {

  // todo move to DB
  public static float chargedWt;
  public static long onTime;
  public static long offTime;

  @Autowired
  private DeviceService deviceService;

  public static final long checkIntervalInMillis = 1000;

  @Scheduled(fixedDelay = checkIntervalInMillis)
  public void sumPower() throws Exception {
    long now = currentTimeMillis();

    if (isOn && now > offTime) {
      //todo: add error handling
      deviceService.off();
      return;
    }

    String power = deviceService.getPower();
    System.out.println("charging power (watts): " + power);
    float chargingTimeSecs = (now - onTime) / 1000;
    System.out.println("chargingTimeSecs: " + chargingTimeSecs);

    float powerWt = Float.parseFloat(power);
    chargedWt += powerWt / (3600 * 1000F / checkIntervalInMillis);

    System.out.println("chargedWt: " + chargedWt);
  }
}