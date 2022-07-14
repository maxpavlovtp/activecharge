package com.km220.dao.station;

import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class StationRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final StationRowMapper stationRowMapper = new StationRowMapper();

  private static final Logger logger = LoggerFactory.getLogger(StationRepository.class);

  public StationRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Transactional(readOnly = true)
  public StationEntity getByNumber(String number) {
    Objects.requireNonNull(number);

    var sql = "SELECT * from station WHERE number = :number";

    return jdbcTemplate.queryForObject(sql, Map.of("number", number),
        stationRowMapper);
  }
}
