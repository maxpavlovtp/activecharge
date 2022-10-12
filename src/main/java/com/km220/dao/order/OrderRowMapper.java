package com.km220.dao.order;

import static com.km220.dao.order.OrderEntity.INVOICE_ID;
import static com.km220.dao.order.OrderEntity.PERIOD_SEC;
import static com.km220.dao.order.OrderEntity.STATE;
import static com.km220.dao.order.OrderEntity.station_number;

import com.km220.dao.DatabaseEntityRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class OrderRowMapper extends DatabaseEntityRowMapper<OrderEntity> {

  private static final Supplier<OrderEntity> supplier = OrderEntity::new;

  public OrderRowMapper() {
    super(supplier);
  }

  public OrderRowMapper(final String alias) {
    super(supplier, alias);
  }

  @Override
  public OrderEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    OrderEntity orderEntity = super.mapRow(rs, rowNum);

    orderEntity.setInvoiceId(rs.getString(name(INVOICE_ID)));
    orderEntity.setState(OrderState.valueOf(rs.getString(name(STATE))));
		orderEntity.setStationNumber(rs.getString(name(station_number)));
		orderEntity.setPeriodSec(rs.getInt(name(PERIOD_SEC)));

    return orderEntity;
  }
}
