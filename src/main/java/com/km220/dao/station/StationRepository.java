package com.km220.dao.station;

import com.km220.dao.job.ChargingJobRowMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class StationRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final StationRowMapper stationRowMapper;
  private final StationJobRowMapper stationJobRowMapper;

  private static final String STATION_ALIAS = "s_";
  private static final String CHARGING_JOB_ALIAS = "j_";

  public StationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.stationRowMapper = new StationRowMapper();
    this.stationJobRowMapper = new StationJobRowMapper(STATION_ALIAS,
        new ChargingJobRowMapper(new StationRowMapper(STATION_ALIAS), CHARGING_JOB_ALIAS));
  }

  @Transactional(readOnly = true)
  public StationEntity getByNumber(String number) {
    Objects.requireNonNull(number);

    var sql = "SELECT * from station WHERE number = :number";

    return DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("number", number),
        stationRowMapper));
  }

  @Transactional(readOnly = true)
  public StationEntity getByDeviceId(String deviceId) {
    Objects.requireNonNull(deviceId);

    var sql = "SELECT * from station WHERE provider_device_id = :deviceId";

    return DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("deviceId", deviceId),
        stationRowMapper));
  }

  @Transactional(readOnly = true)
  public List<StationEntity> findAll() {
    var sql = """
        SELECT
            s.id as s_id, s.name as s_name, s.number as s_number, s.cost_per_hour as s_cost_per_hour,
            s.provider_device_id as s_provider_device_id, s.created_on as s_created_on,
            s.updated_on as s_updated_on, j.id as j_id, j.number as j_number, j.charged_wt_h as j_charged_wt_h,
            j.power_wt as j_power_wt, j.voltage as j_voltage, j.reason as j_reason, j.state as j_state,
            j.created_on as j_created_on, j.updated_on as j_updated_on, j.period_sec as j_period_sec,
            j.stopped_on as j_stopped_on
        FROM STATION s
        LEFT OUTER JOIN
                (SELECT * from CHARGING_JOB WHERE state = 'IN_PROGRESS') j
        ON s.id = j.station_id;
        """;

    return jdbcTemplate.query(sql, Map.of(), stationJobRowMapper);
  }

}
