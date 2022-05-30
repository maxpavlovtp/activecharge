package com.km220.ewelink;

import java.net.http.HttpClient;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class EwelinkClient {

  private EwelinkParameters parameters;
  private String applicationId;
  private String applicationSecret;
  private HttpClient httpClient;

  private static final String APPLICATION_ID = "YzfeftUVcZ6twZw1OoVKPRFYTrGEg01Q";
  private static final String APPLICATION_SECRET = "4G91qSoboqYO4Y0XJ0LPPKIsq8reHdfa";

  @Builder
  @SuppressWarnings("unused")
  private EwelinkClient(@NonNull final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    this.parameters = parameters;
    this.applicationId = Optional.ofNullable(applicationId).orElse(APPLICATION_ID);
    this.applicationSecret = Optional.ofNullable(applicationSecret).orElse(APPLICATION_SECRET);
    this.httpClient = Optional.ofNullable(httpClient).orElseGet(HttpClient::newHttpClient);
  }

  public EwelinkDeviceApi devices() {
    return new EwelinkDeviceApi(parameters, applicationId, applicationSecret, httpClient);
  }
}
