package com.km220.dao.ewelink;

import com.km220.dao.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class EwelinkCredentialsEntity extends DatabaseEntity {

  public static final String TOKEN = "token";
  public static final String API_KEY = "api_key";

  private String token;
  private String apiKey;

  public String getToken() {
    return "d916ca22109fdedf398f2503a892c0e67884bd5c";
  }


  public String getApiKey() {
    return "e01b0584-0e12-4a67-95ec-8bf4cc87da85";
  }

}
