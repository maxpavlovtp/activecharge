package com.km220.dao.job;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
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
    assertEquals("stage", stationEntity.getName());
    assertEquals("2", stationEntity.getNumber());
    assertEquals("10013bb124", stationEntity.getDeviceId());
  }
}
