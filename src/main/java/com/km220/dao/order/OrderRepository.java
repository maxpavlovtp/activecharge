package com.km220.dao.order;

import com.km220.dao.ChargerDatabaseException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final OrderRowMapper orderRowMapper = new OrderRowMapper();

  private static final Logger logger = LoggerFactory.getLogger(OrderRepository.class);

  public OrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Transactional(readOnly = true)
  public OrderEntity getByInvoiceId(String invoiceId) {
    Objects.requireNonNull(invoiceId);

    var sql = "SELECT * from order_220 WHERE invoice_id = :invoiceId";

    return DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("invoice_id", invoiceId),
        orderRowMapper));
  }

  private static final String INSERT_SQL = """
      INSERT into order_220(invoice_id)
      VALUES ((SELECT id FROM station WHERE number = :station_number), :period_sec);
      """;
  public UUID add(String invoiceId) {
    Objects.requireNonNull(invoiceId);

    var parameters = new MapSqlParameterSource()
        .addValue("invoice_id", invoiceId);
    final KeyHolder keyHolder = new GeneratedKeyHolder();

    if (jdbcTemplate.update(INSERT_SQL, parameters, keyHolder) > 0) {
      logger.info("Charging job created in DB. Station number = {}, period = {} seconds",
          stationNumber,
          periodSeconds);

      return (UUID) keyHolder.getKeyList().get(0).get("id");
    }

    throw new ChargerDatabaseException("Couldn't create charging job. Station number = "
        + stationNumber);
  }

}
