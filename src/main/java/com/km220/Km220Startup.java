package com.km220;

import com.km220.dao.station.StationRepository;
import com.km220.ewelink.EwelinkClient;
import com.km220.ewelink.model.v2.DeviceList;
import com.km220.ewelink.model.v2.ResponseV2;
import com.km220.service.metrics.StationMetricsCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Km220Startup implements ApplicationListener<ApplicationReadyEvent> {

  private final EwelinkClient ewelinkClient;
  private final StationMetricsCollector stationMetricsCollector;
  private final StationRepository stationRepository;

  public Km220Startup(final EwelinkClient ewelinkClient,
      final StationMetricsCollector stationMetricsCollector,
      final StationRepository stationRepository) {
    this.ewelinkClient = ewelinkClient;
    this.stationMetricsCollector = stationMetricsCollector;
    this.stationRepository = stationRepository;
  }

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    log.info("Request all devices state..");
    ResponseV2<DeviceList> response = ewelinkClient.devicesV2()
        .getStatus()
        .join();
    if (response.getError() > 0) {
      log.error("Couldn't get device statuses. Reason: " + response.getMsg());
      return;
    }
    for (DeviceList.Entry e : response.getData().getThingList()) {
      var station = stationRepository.getByDeviceId(e.getItemData().getDeviceid());
      if (station != null) {
        stationMetricsCollector.updateOnlineMetric(station, e.getItemData().getOnline());
      }
    }
  }
}
