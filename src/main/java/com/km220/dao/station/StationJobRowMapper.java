package com.km220.dao.station;

import com.km220.dao.DatabaseEntity;
import com.km220.dao.job.ChargingJobRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StationJobRowMapper extends StationRowMapper {

  private final ChargingJobRowMapper chargingJobRowMapper;

  public StationJobRowMapper(final String alias, final ChargingJobRowMapper chargingJobRowMapper) {
    super(alias);
    this.chargingJobRowMapper = chargingJobRowMapper;
  }

  @Override
  public StationEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    var stationEntity = super.mapRow(rs, rowNum);

    rs.getString(chargingJobRowMapper.name(DatabaseEntity.ID));
    if (!rs.wasNull()) {
      var chargingJobEntity = chargingJobRowMapper.mapRow(rs, rowNum);
      stationEntity.setActiveJob(chargingJobEntity);
    }

    return stationEntity;
  }
}
