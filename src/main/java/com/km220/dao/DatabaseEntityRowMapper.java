package com.km220.dao;

import static com.km220.dao.DatabaseEntity.CREATED_ON;
import static com.km220.dao.DatabaseEntity.ID;
import static com.km220.dao.DatabaseEntity.UPDATED_ON;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import org.springframework.jdbc.core.RowMapper;

public class DatabaseEntityRowMapper<T extends DatabaseEntity> implements RowMapper<T> {

  private final Supplier<T> databaseEntitySupplier;
  private String alias = "";

  protected static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
      .appendPattern("yyyy-MM-dd HH:mm:ss")
      .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 6, true)
      .appendPattern("X")
      .toFormatter(Locale.ROOT);

  public DatabaseEntityRowMapper(final Supplier<T> databaseEntitySupplier) {
    this.databaseEntitySupplier = Objects.requireNonNull(databaseEntitySupplier);
  }

  public DatabaseEntityRowMapper(final Supplier<T> databaseEntitySupplier, String alias) {
    this.databaseEntitySupplier = Objects.requireNonNull(databaseEntitySupplier);
    this.alias = Objects.requireNonNull(alias);
  }

  @Override
  public T mapRow(ResultSet rs, int rowNum) throws SQLException {
    var databaseEntity = databaseEntitySupplier.get();

    databaseEntity.setId(UUID.fromString(rs.getString(name(ID))));
    databaseEntity.setCreatedOn(
        OffsetDateTime.parse(rs.getString(name(CREATED_ON)), dateTimeFormatter));
    databaseEntity.setUpdatedOn(
        OffsetDateTime.parse(rs.getString(name(UPDATED_ON)), dateTimeFormatter));

    return databaseEntity;
  }

  public String name(String columnName) {
    return alias + columnName;
  }
}
