package com.km220;


import static com.km220.PowerAggregationJob.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.km220.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PowerAggregationJobTest {

  @Autowired
  DeviceService deviceService;

  @Test
  public void chargingWtAverageWtHTest() throws Exception {
    // when
    int testTimeSecs = 30;
    deviceService.on(testTimeSecs);
    Thread.sleep(testTimeSecs * 1000 + 3000);

    // then
    float delta = ((chargingWtAverageWtH - powerWt) / powerWt) * 100;
    System.out.println("delta: " + delta);
    assertThat(delta < 25).isTrue();
  }
}