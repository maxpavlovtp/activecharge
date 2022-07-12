package com.km220.dao.job;

import static com.km220.dao.job.ChargingJobEntity.CHARGED_WT;
import static com.km220.dao.job.ChargingJobEntity.CHARGING_WT;
import static com.km220.dao.job.ChargingJobEntity.NUMBER;
import static com.km220.dao.job.ChargingJobEntity.PERIOD;
import static com.km220.dao.job.ChargingJobEntity.REASON;
import static com.km220.dao.job.ChargingJobEntity.STATE;

import com.km220.dao.DatabaseEntityRowMapper;
import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class ChargingJobRowMapper extends DatabaseEntityRowMapper<ChargingJobEntity> {

  private final StationRowMapper stationRowMapper;

  private static final Supplier<ChargingJobEntity> supplier = ChargingJobEntity::new;

  public ChargingJobRowMapper(final StationRowMapper stationRowMapper) {
    super(supplier);
    this.stationRowMapper = stationRowMapper;
  }

  public ChargingJobRowMapper(final StationRowMapper stationRowMapper, String alias) {
    super(supplier, alias);
    this.stationRowMapper = stationRowMapper;
  }

  @Override
  public ChargingJobEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
    ChargingJobEntity job = super.mapRow(rs, rowNum);

    job.setNumber(rs.getInt(name(NUMBER)));
    job.setState(ChargingJobState.valueOf(rs.getString(name(STATE))));
    job.setReason(rs.getString(name(REASON)));
    job.setChargedWt(rs.getFloat(name(CHARGED_WT)));
    job.setChargingWt(rs.getFloat(name(CHARGING_WT)));
    job.setPeriodSec(rs.getInt(name(PERIOD)));

    StationEntity station = stationRowMapper.mapRow(rs, rowNum);
    job.setStation(station);

    return job;
  }
}
