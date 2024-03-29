package com.km220.ewelink;

import com.km220.ewelink.v2.EwelinkDeviceApiV2;
import com.km220.ewelink.v2.WSEwelinkDeviceApiV2;
import java.net.http.HttpClient;
import java.time.Duration;
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
  private CredentialsStorage credentialsStorage;
  private HttpClient httpClient;
  private int httpRequestTimeoutSec;

  @Builder
  @SuppressWarnings("unused")
  private EwelinkClient(@NonNull final EwelinkParameters parameters,
      @NonNull final String applicationId,
      @NonNull final String applicationSecret,
      @NonNull final CredentialsStorage credentialsStorage,
      final HttpClient httpClient,
      final int httpRequestTimeoutSec) {
    this.parameters = parameters;
    this.applicationId = applicationId;
    this.applicationSecret = applicationSecret;
    this.credentialsStorage = credentialsStorage;
    this.httpRequestTimeoutSec = httpRequestTimeoutSec;
    this.httpClient = Optional.ofNullable(httpClient).orElseGet(() -> HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(httpRequestTimeoutSec))
        .build());
  }

  public EwelinkDeviceApi devices() {
    return new EwelinkDeviceApi(parameters, applicationId, applicationSecret, httpClient);
  }

  public EwelinkDeviceApiV2 devicesV2() {
    return new EwelinkDeviceApiV2(parameters, applicationId, applicationSecret,
        credentialsStorage, httpClient, httpRequestTimeoutSec);
  }

  public WSEwelinkDeviceApiV2 wsDevicesV2(WSClientListener wsClientListener) {
    return new WSEwelinkDeviceApiV2(parameters, applicationId, applicationSecret,
        credentialsStorage, wsClientListener, httpClient, httpRequestTimeoutSec);
  }

  public WSEwelinkDeviceApi wsDevices() {
    return new WSEwelinkDeviceApi(parameters, applicationId, applicationSecret, httpClient);
  }
}
