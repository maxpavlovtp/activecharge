package com.km220.dao.ewelink;

import static com.km220.dao.ewelink.EwelinkCredentialsEntity.API_KEY;
import static com.km220.dao.ewelink.EwelinkCredentialsEntity.TOKEN;

import com.km220.dao.DatabaseEntityRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class EwelinkCredentialsRowMapper extends DatabaseEntityRowMapper<EwelinkCredentialsEntity> {

  private static final Supplier<EwelinkCredentialsEntity> supplier = EwelinkCredentialsEntity::new;

  public EwelinkCredentialsRowMapper() {
    super(supplier);
  }

  public EwelinkCredentialsRowMapper(final String alias) {
    super(supplier, alias);
  }

  @Override
  public EwelinkCredentialsEntity mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    EwelinkCredentialsEntity ewelinkCredentialsEntity = super.mapRow(rs, rowNum);

    ewelinkCredentialsEntity.setToken(rs.getString(name(TOKEN)));
    ewelinkCredentialsEntity.setApiKey(rs.getString(name(API_KEY)));

    return ewelinkCredentialsEntity;
  }
}
