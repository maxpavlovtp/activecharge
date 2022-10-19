package com.km220.dao.station;

import static com.km220.dao.station.StationEntity.COST_PER_HOUR;
import static com.km220.dao.station.StationEntity.NAME;
import static com.km220.dao.station.StationEntity.NUMBER;
import static com.km220.dao.station.StationEntity.PROVIDER_DEVICE_ID;

import com.km220.dao.DatabaseEntityRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class StationRowMapper extends DatabaseEntityRowMapper<StationEntity> {

  private static final Supplier<StationEntity> supplier = StationEntity::new;

  public StationRowMapper() {
    super(supplier);
  }

  public StationRowMapper(final String alias) {
    super(supplier, alias);
  }

  @Override
  public StationEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    StationEntity stationEntity = super.mapRow(rs, rowNum);

    stationEntity.setNumber(rs.getString(name(NUMBER)));
    stationEntity.setName(rs.getString(name(NAME)));
    stationEntity.setDeviceId(rs.getString(name(PROVIDER_DEVICE_ID)));
		stationEntity.setCostPerHour(rs.getString(name(COST_PER_HOUR)));

    return stationEntity;
  }
}
