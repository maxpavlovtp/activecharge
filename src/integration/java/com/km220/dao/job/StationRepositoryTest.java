package com.km220.dao.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJdbcTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
@ActiveProfiles("integration-test")
class StationRepositoryTest {

  private static final String STATION_NUMBER = "2";

  @Autowired
  private StationRepository stationRepository;

  @Test
  void getByNumber_shouldFindStationByNumber() {
    StationEntity stationEntity = stationRepository.getByNumber(STATION_NUMBER);

    assertNotNull(stationEntity);
    assertEquals("2-local-TyhranHome", stationEntity.getName());
    assertEquals("2", stationEntity.getNumber());
    assertEquals("1000d61c41", stationEntity.getDeviceId());
    assertNull(stationEntity.getActiveJob());
  }

  @Test
  @Sql("/dao/job/init_scan.sql")
  void findAll_shouldReturnAllStations() {
    List<StationEntity> stations = stationRepository.findAll();

    assertEquals(5, stations.size());

    //TODO: implement
  }
}
