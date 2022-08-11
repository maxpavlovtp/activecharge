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
public final class EwelinkCredentialsEntity extends DatabaseEntity {

  public static final String TOKEN = "token";
  public static final String API_KEY = "api_key";

  private String token;
  private String apiKey;

}
