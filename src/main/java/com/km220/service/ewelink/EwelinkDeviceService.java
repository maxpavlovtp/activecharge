package com.km220.service.ewelink;

import static com.km220.PowerAggregationJob.onTime;
import static com.km220.service.ewelink.PowerLimitOverloadService.OVERLOAD_LIMIT_TIMER_SECS;
import static java.lang.System.currentTimeMillis;

import com.km220.PowerAggregationJob;
import com.km220.ewelink.EwelinkClient;
import com.km220.ewelink.model.device.SwitchState;
import com.km220.ewelink.model.v2.DeviceV2;
import com.km220.model.DeviceStatus;
import com.km220.service.DeviceCache;
import com.km220.service.DeviceException;
import com.km220.service.DeviceService;
import java.util.Locale;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EwelinkDeviceService implements DeviceService {

  private final EwelinkClient ewelinkClient;

  public EwelinkDeviceService(final EwelinkClient ewelinkClient) {
    this.ewelinkClient = ewelinkClient;
  }

  @Override
  public long getChargingDurationLeftSecs() {
    return PowerAggregationJob.chargingDurationLeftSecs;
  }

  @Override
  public float getChargedWt() {
    return DeviceCache.chargedWt;
  }

  @Override
  public DeviceStatus getStatus(String deviceId) {
    return ewelinkClient.devicesV2()
        .getStatus(Objects.requireNonNull(deviceId))
        .thenApply(response -> DeviceUtils.convert(deviceId, response))
        .join();
  }

  @Override
  public void toggleOn(String deviceId, int chargeTimeSec) {
    //TODO: refactor this
    PowerAggregationJob.chargingDurationSecs = 0;
    DeviceCache.chargedWt = 0;
    PowerAggregationJob.onTime = currentTimeMillis();
    PowerAggregationJob.offTime = onTime + 1000L * chargeTimeSec;

    DeviceV2 response = ewelinkClient.devicesV2()
        .toggle(Objects.requireNonNull(deviceId), SwitchState.ON,
            OVERLOAD_LIMIT_TIMER_SECS + chargeTimeSec)
        .join();
    if (response.getError() > 0) {
      throw new DeviceException(
          String.format(Locale.ROOT, "Error on switching device. Device id = %s", deviceId));
    }

    DeviceCache.isOn = true;
  }

  @Override
  public void toggleOff(String deviceId, int chargeTimeSec) {
    DeviceV2 response = ewelinkClient.devicesV2()
        .toggle(Objects.requireNonNull(deviceId), SwitchState.OFF, chargeTimeSec)
        .join();
    if (response.getError() > 0) {
      throw new DeviceException(
          String.format(Locale.ROOT, "Error on switching device. Device id = %s", deviceId));
    }

    DeviceCache.isOn = false;
  }
}
