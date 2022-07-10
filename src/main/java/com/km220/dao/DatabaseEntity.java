package com.km220.dao;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class DatabaseEntity {

  public static final String ID = "id";
  public static final String CREATED_ON = "created_on";
  public static final String UPDATED_ON = "updated_on";

  protected UUID id;
  protected OffsetDateTime createdOn;
  protected OffsetDateTime updatedOn;
}
