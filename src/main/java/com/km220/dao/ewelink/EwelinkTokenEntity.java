package com.km220.dao.ewelink;

import com.km220.dao.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class EwelinkTokenEntity extends DatabaseEntity {

  public static final String TOKEN = "token";

  private String token;

}
