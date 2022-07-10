package com.km220.dao.job;

import static com.km220.dao.job.ChargingJobEntity.CHARGED_WT;
import static com.km220.dao.job.ChargingJobEntity.CHARGING_WT;
import static com.km220.dao.job.ChargingJobEntity.NUMBER;
import static com.km220.dao.job.ChargingJobEntity.REASON;
import static com.km220.dao.job.ChargingJobEntity.STATE;

import com.km220.dao.ChargerDatabaseException;
import com.km220.dao.station.StationRowMapper;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ChargingJobRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final StationRowMapper stationRowMapper = new StationRowMapper(STATION_ALIAS);
  private final ChargingJobRowMapper chargingJobRowMapper = new ChargingJobRowMapper(
      stationRowMapper, CHARGING_JOB_ALIAS);

  private static final String STATION_ALIAS = "s_";
  private static final String CHARGING_JOB_ALIAS = "j_";

  private static final String QUERY = """
      SELECT j.id as j_id, j.number as j_number, j.charged_wt as j_charged_wt,
          j.charging_wt as j_charging_wt, j.reason as j_reason, j.state as j_state,
          j.created_on as j_created_on, j.updated_on as j_updated_on,
          s.id as s_id, s.number as s_number, s.name as s_name, s.provider_device_id as s_provider_device_id,
          s.updated_on as s_updated_on, s.created_on as s_created_on
        FROM charging_job j
        JOIN station s on j.station_id = s.id
      """;

  public ChargingJobRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public ChargingJobEntity getById(UUID jobId) {
    Objects.requireNonNull(jobId);

    var sql = QUERY + " WHERE j.id = :id";

    return jdbcTemplate.queryForObject(sql, Map.of("id", jobId),
        chargingJobRowMapper);
  }

  public ChargingJobEntity getByNumber(String number) {
    Objects.requireNonNull(number);

    var sql = QUERY + " WHERE j.number = :number";

    return jdbcTemplate.queryForObject(sql, Map.of("number", number),
        chargingJobRowMapper);
  }

  public List<ChargingJobEntity> scan(ChargingJobState state, int batchSize) {
    Objects.requireNonNull(state);

    var sql = QUERY + """
        WHERE j.state = :state
        ORDER BY j.updated_on
        LIMIT %s
        FOR UPDATE SKIP LOCKED
        """.formatted(batchSize);

    var sqlParameterSource = new MapSqlParameterSource();
    sqlParameterSource.addValue("state", state, Types.OTHER);

    return jdbcTemplate.query(sql, sqlParameterSource, chargingJobRowMapper);
  }

  public UUID add(String stationNumber) {
    Objects.requireNonNull(stationNumber);

    var sql = """
        INSERT into charging_job(station_id)
        VALUES ((SELECT id FROM station WHERE number = :station_number));
        """;
    var parameters = new MapSqlParameterSource()
        .addValue("station_number", stationNumber);
    final KeyHolder keyHolder = new GeneratedKeyHolder();

    if (jdbcTemplate.update(sql, parameters, keyHolder) > 0) {
      return (UUID) keyHolder.getKeyList().get(0).get("id");
    }

    throw new ChargerDatabaseException("Couldn't create charging job. Station number = "
        + stationNumber);
  }

  public void update(ChargingJobEntity chargingJob) {
    Objects.requireNonNull(chargingJob);

    var sql = """
        UPDATE charging_job SET state = :state, reason = :reason, charging_wt = :charging_wt,
        charged_wt = :charged_wt where number = :number
        """;
    var parameters = Map.of(
        STATE, chargingJob.getState().toString(),
        REASON, chargingJob.getReason(),
        CHARGING_WT, chargingJob.getChargingWt(),
        CHARGED_WT, chargingJob.getChargedWt(),
        NUMBER, chargingJob.getNumber());

    if (jdbcTemplate.update(sql, parameters) <= 0) {
      throw new ChargerDatabaseException("Couldn't update charging job. Job number = "
          + chargingJob.getNumber());
    }
  }
}
