package com.km220.dao.ewelink;

import static com.km220.dao.ewelink.EwelinkTokenEntity.TOKEN;

import com.km220.dao.DatabaseEntityRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class EwelinkTokenRowMapper extends DatabaseEntityRowMapper<EwelinkTokenEntity> {

  private static final Supplier<EwelinkTokenEntity> supplier = EwelinkTokenEntity::new;

  public EwelinkTokenRowMapper() {
    super(supplier);
  }

  public EwelinkTokenRowMapper(final String alias) {
    super(supplier, alias);
  }

  @Override
  public EwelinkTokenEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    EwelinkTokenEntity ewelinkTokenEntity = super.mapRow(rs, rowNum);

    ewelinkTokenEntity.setToken(rs.getString(name(TOKEN)));

    return ewelinkTokenEntity;
  }
}
