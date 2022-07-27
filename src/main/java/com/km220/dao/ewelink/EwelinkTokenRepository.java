package com.km220.dao.ewelink;

import static com.km220.dao.ewelink.EwelinkTokenEntity.TOKEN;

import com.km220.dao.ChargerDatabaseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EwelinkTokenRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final EwelinkTokenRowMapper ewelinkTokenRowMapper = new EwelinkTokenRowMapper();

  private static final Logger logger = LoggerFactory.getLogger(EwelinkTokenRepository.class);

  private static final String UPDATE_SQL = "UPDATE ewelink_token SET token = :token";

  public EwelinkTokenRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public EwelinkTokenEntity get(boolean lock) {
    var sql = "SELECT * from ewelink_token";
    if (lock) {
      sql += " FOR UPDATE";
    }

    return jdbcTemplate.queryForObject(sql, Map.of(), ewelinkTokenRowMapper);
  }

  public void update(EwelinkTokenEntity tokenEntity) {
    Objects.requireNonNull(tokenEntity);

    var parameters = new HashMap<String, Object>();
    parameters.put(TOKEN, tokenEntity.getToken());

    if (jdbcTemplate.update(UPDATE_SQL, parameters) <= 0) {
      throw new ChargerDatabaseException("Couldn't update ewelink token.");
    }
  }
}
