package com.km220.dao.job;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
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
class ChargingJobRepositoryTest {

  private static final String STATION_NUMBER = "2";

  @Autowired
  private ChargingJobRepository chargingJobRepository;

  @Test
  void add_shouldAddChargingJob() {
    Instant now = Instant.now();

    UUID uuid = chargingJobRepository.add(STATION_NUMBER, 10);

    assertNotNull(uuid);

    ChargingJobEntity job = chargingJobRepository.getById(uuid);

    assertNotNull(job);
    assertTrue(job.getNumber() > 0);
    assertEquals("", job.getReason());
    assertEquals(ChargingJobState.IN_PROGRESS, job.getState());
    assertEquals(0f, job.getChargingWt(), 0.00001f);
    assertEquals(0f, job.getChargedWt(), 0.00001f);
    assertEquals(10, job.getPeriodSec());
    assertNull(job.getStoppedOn());
    assertTrue(job.getCreatedOn().isAfter(now.atOffset(ZoneOffset.UTC)));
    assertTrue(job.getUpdatedOn().isAfter(now.atOffset(ZoneOffset.UTC)));

    assertNotNull(job.getStation());
    assertEquals("stage", job.getStation().getName());
    assertEquals("2", job.getStation().getNumber());
    assertEquals("10013bb124", job.getStation().getDeviceId());
  }

  @Test
  void update_shouldUpdateChargingJob() {
    Instant onCreate = Instant.now();

    UUID uuid = chargingJobRepository.add(STATION_NUMBER, 10);

    assertNotNull(uuid);

    ChargingJobEntity job = chargingJobRepository.getById(uuid);

    assertNotNull(job);
    assertTrue(job.getNumber() > 0);
    assertEquals("", job.getReason());
    assertEquals(ChargingJobState.IN_PROGRESS, job.getState());
    assertEquals(0f, job.getChargingWt(), 0.00001f);
    assertEquals(0f, job.getChargedWt(), 0.00001f);
    assertEquals(10, job.getPeriodSec());
    assertNull(job.getStoppedOn());
    assertTrue(job.getCreatedOn().isAfter(onCreate.atOffset(ZoneOffset.UTC)));
    assertTrue(job.getUpdatedOn().isAfter(onCreate.atOffset(ZoneOffset.UTC)));

    job.setChargingWt(1f);
    job.setChargedWt(2f);
    job.setReason("OK");
    job.setState(ChargingJobState.DONE);

    chargingJobRepository.update(job);

    job = chargingJobRepository.getById(uuid);

    assertNotNull(job);
    assertTrue(job.getNumber() > 0);
    assertEquals("OK", job.getReason());
    assertEquals(ChargingJobState.DONE, job.getState());
    assertEquals(1f, job.getChargingWt(), 0.00001f);
    assertEquals(2f, job.getChargedWt(), 0.00001f);
    assertNull(job.getStoppedOn());

    assertNotNull(job.getStation());
    assertEquals("stage", job.getStation().getName());
    assertEquals("2", job.getStation().getNumber());
    assertEquals("10013bb124", job.getStation().getDeviceId());
  }

  @Test
  @Sql("/dao/job/init_scan.sql")
  void scan_shouldReturnNotLockedRecords() {
    int batchSize = 10;
    List<ChargingJobEntity> jobs = chargingJobRepository.scan(ChargingJobState.IN_PROGRESS,
        batchSize);

    assertThat(jobs, hasItems(
            allOf(
                hasProperty("number", is(1L)),
                hasProperty("state", is(ChargingJobState.IN_PROGRESS)),
                hasProperty("chargingWt", is(1.0f)),
                hasProperty("chargedWt", is(2.0f)),
                hasProperty("periodSec", is(10)),
                hasProperty("stoppedOn", is(nullValue())),
                hasProperty("station",
                    allOf(
                        hasProperty("name", is("test1")),
                        hasProperty("number", is("1")),
                        hasProperty("deviceId", is("device1"))
                    )
                )
            ),
            allOf(
                hasProperty("number", is(3L)),
                hasProperty("state", is(ChargingJobState.IN_PROGRESS)),
                hasProperty("chargingWt", is(5.0f)),
                hasProperty("chargedWt", is(6.0f)),
                hasProperty("periodSec", is(10)),
                hasProperty("stoppedOn", is(nullValue())),
                hasProperty("station",
                    allOf(
                        hasProperty("name", is("test2")),
                        hasProperty("number", is("2")),
                        hasProperty("deviceId", is("device2"))
                    )
                )
            ),
            allOf(
                hasProperty("number", is(4L)),
                hasProperty("state", is(ChargingJobState.IN_PROGRESS)),
                hasProperty("chargingWt", is(7.0f)),
                hasProperty("chargedWt", is(8.0f)),
                hasProperty("periodSec", is(10)),
                hasProperty("stoppedOn", is(nullValue())),
                hasProperty("station",
                    allOf(
                        hasProperty("name", is("test3")),
                        hasProperty("number", is("3")),
                        hasProperty("deviceId", is("device3"))
                    )
                )
            )
        )
    );
  }
}
