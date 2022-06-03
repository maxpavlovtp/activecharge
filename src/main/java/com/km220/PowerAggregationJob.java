package com.km220;

import static com.km220.service.DeviceService.isOn;
import static java.lang.System.currentTimeMillis;

import com.km220.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PowerAggregationJob {
  @Autowired
  private DeviceService deviceService;

  // todo move to DB
  public static float chargedWt;
  public static float chargingWtAverageWtH;
  public static long onTime;
  public static long offTime;
  public static long chargeDurationSecs;
  public static long chargingDurationSecs;
  public static long chargingDurationLeftSecs;

  public static final long CHECK_INTERVAL_IN_MILLIS = 1000;

  @Scheduled(fixedDelay = CHECK_INTERVAL_IN_MILLIS)
  public void sumPower() throws Exception {
    if (!isOn) {
      return;
    }

    long now = currentTimeMillis();
    if (isOn && now > offTime) {
      //todo: add error handling
      deviceService.off();
      return;
    }

    String power = deviceService.getPower();
    System.out.println("charging power (watts): " + power);
    chargingDurationSecs = (int) ((now - onTime) / 1000);
    System.out.println("chargingDurationSecs: " + chargingDurationSecs);
    chargingDurationLeftSecs = (offTime - now) / 1000;
    System.out.println("chargingDurationLeftSecs: " + chargingDurationLeftSecs);
    chargingWtAverageWtH = chargedWt * 3600 / chargingDurationSecs;
    System.out.println("chargingWtAverageWtH: " + chargingWtAverageWtH);

    float powerWt = Float.parseFloat(power);
    chargedWt += powerWt / (3600 * 1000F / CHECK_INTERVAL_IN_MILLIS);

    System.out.println("chargedWt: " + chargedWt);
  }
}
