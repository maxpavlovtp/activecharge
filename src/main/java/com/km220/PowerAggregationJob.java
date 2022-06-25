package com.km220;

import static java.lang.System.currentTimeMillis;

import com.km220.service.DeviceService;
import com.km220.service.PowerLimitOverloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PowerAggregationJob {

  @Autowired
  private DeviceService deviceService;

  @Autowired
  private PowerLimitOverloadService powerLimitOverloadService;

  // todo move to DB
  public static volatile boolean isOn;
  public static volatile float chargedWt;
  public static volatile float chargingWtAverageWtH;
  public static volatile float powerWt;
  public static volatile long onTime;
  public static volatile long offTime;
  public static volatile long chargingDurationSecs;
  public static volatile long chargingDurationLeftSecs;

  public static final long CHECK_INTERVAL_MILLIS = 1000;

  @Scheduled(fixedDelay = CHECK_INTERVAL_MILLIS)
  public void sumPower() {
    long now = currentTimeMillis();
    if (!isOn) {
      return;
    }

    if (isOn && now > offTime) {
      //todo: add error handling
      try {
        deviceService.off();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    }

    try {
      powerWt = Float.parseFloat(deviceService.getPower(true));
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("charging power (watts): " + powerWt);

    long apiCallMillis = currentTimeMillis() - now;
    long checkIntervalMillisReal = CHECK_INTERVAL_MILLIS + apiCallMillis;
    chargedWt += powerWt / (3600 * 1000F / checkIntervalMillisReal);
    System.out.println("chargedWt: " + chargedWt);

    chargingDurationSecs += checkIntervalMillisReal / 1000;
    System.out.println("chargingDurationSecs: " + chargingDurationSecs);

    chargingWtAverageWtH = chargedWt * 3600 / chargingDurationSecs;
    System.out.println("chargingWtAverageWtH: " + chargingWtAverageWtH);

    chargingDurationLeftSecs = (offTime - now) / 1000;
    System.out.println("chargingDurationLeftSecs: " + chargingDurationLeftSecs);

    System.out.println(
        "isPowerLimitOvelrloaded: " + powerLimitOverloadService.isPowerLimitOvelrloaded());
    System.out.println(
        "getPowerLimit: " + powerLimitOverloadService.getPowerLimit());
  }
}
