package com.km220.dao.ewelink;

import com.km220.dao.DatabaseEntity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;

@SuperBuilder
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class EwelinkCredentialsEntity extends DatabaseEntity {

  @Value("${ewelink.useOAuth}")
  private boolean useOAuth;
  @Value("${ewelink.oAuth-token}")
  private String oAuthToken;
  @Value("${ewelink.oAuth-apiKey}")
  private String oAuthApiKey;


  public static final String TOKEN = "token";
  public static final String API_KEY = "api_key";

  private String token;
  private String apiKey;

  public String getToken() {
    return useOAuth ? oAuthToken : token;
  }

  public String getApiKey() {
    return useOAuth ? oAuthApiKey : apiKey;
  }
}
