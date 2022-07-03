package com.km220.ewelink;

import com.km220.ewelink.v2.EwelinkDeviceApi2;
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

  @Builder
  @SuppressWarnings("unused")
  private EwelinkClient(@NonNull final EwelinkParameters parameters,
      @NonNull final String applicationId,
      @NonNull final String applicationSecret, final HttpClient httpClient) {
    this.parameters = parameters;
    this.applicationId = applicationId;
    this.applicationSecret = applicationSecret;
    this.httpClient = Optional.ofNullable(httpClient).orElseGet(HttpClient::newHttpClient);
  }

  public EwelinkDeviceApi devices() {
    return new EwelinkDeviceApi(parameters, applicationId, applicationSecret, httpClient);
  }

  public EwelinkDeviceApi2 devicesV2() {
    return new EwelinkDeviceApi2(parameters, applicationId, applicationSecret,
        httpClient);
  }

  public WSEwelinkDeviceApi wsDevices() {
    return new WSEwelinkDeviceApi(parameters, applicationId, applicationSecret, httpClient);
  }
}
