package com.km220.dao.ewelink;

import static com.km220.dao.ewelink.EwelinkCredentialsEntity.API_KEY;
import static com.km220.dao.ewelink.EwelinkCredentialsEntity.TOKEN;

import com.km220.dao.ChargerDatabaseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EwelinkCredentialsRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final EwelinkCredentialsRowMapper ewelinkCredentialsRowMapper = new EwelinkCredentialsRowMapper();

  private static final Logger logger = LoggerFactory.getLogger(EwelinkCredentialsRepository.class);

  private static final String UPDATE_SQL = "UPDATE ewelink_token SET token = :token, api_key = :api_key";

  public EwelinkCredentialsRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public EwelinkCredentialsEntity get(boolean lock) {
    var sql = "SELECT * from ewelink_token";
    if (lock) {
      sql += " FOR UPDATE";
    }

    return jdbcTemplate.queryForObject(sql, Map.of(), ewelinkCredentialsRowMapper);
  }

  public void update(EwelinkCredentialsEntity tokenEntity) {
    Objects.requireNonNull(tokenEntity);

    var parameters = new HashMap<String, Object>();
    parameters.put(TOKEN, tokenEntity.getToken());
    parameters.put(API_KEY, tokenEntity.getApiKey());

    if (jdbcTemplate.update(UPDATE_SQL, parameters) <= 0) {
      throw new ChargerDatabaseException("Couldn't update ewelink token.");
    }
  }
}
