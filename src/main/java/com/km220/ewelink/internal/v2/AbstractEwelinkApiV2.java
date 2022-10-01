package com.km220.ewelink.internal.v2;

import static com.km220.ewelink.internal.utils.HttpUtils.ACCEPT_HEADER;
import static com.km220.ewelink.internal.utils.HttpUtils.AUTHORIZATION_HEADER;
import static com.km220.ewelink.internal.utils.HttpUtils.CONTENT_TYPE_HEADER;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_GET;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_POST;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_STATUS_OK;
import static com.km220.ewelink.internal.utils.JsonUtils.OBJECT_MAPPER;
import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.util.stream.Collectors.joining;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.km220.ewelink.CredentialsStorage;
import com.km220.ewelink.CredentialsStorage.EwelinkCredentials;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.error.EwelinkApiException;
import com.km220.ewelink.error.EwelinkClientException;
import com.km220.ewelink.internal.model.CredentialsRequest;
import com.km220.ewelink.internal.model.v2.CredentialResponseV2;
import com.km220.ewelink.internal.utils.HttpUtils;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.utils.SecurityUtils;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEwelinkApiV2 {

  protected final EwelinkParameters parameters;
  protected final String applicationId;
  protected final String applicationSecret;
  private final CredentialsStorage credentialsStorage;
  protected final HttpClient httpClient;
  private final int httpRequestTimeoutSec;

  private static final String API_BASE_URI = "https://%s-apia.coolkit.cc/v2";
  private static final String LOGIN_URI = "/user/login";

  private static final String X_CK_NONCE_HEADER = "X-CK-Nonce";
  private static final String X_CK_APPID_HEADER = "X-CK-Appid";

  protected AbstractEwelinkApiV2(final EwelinkParameters parameters,
      final String applicationId,
      final String applicationSecret,
      final CredentialsStorage credentialsStorage,
      final HttpClient httpClient,
      final int httpRequestTimeoutSec) {
    this.parameters = Objects.requireNonNull(parameters);
    this.applicationId = Objects.requireNonNull(applicationId);
    this.applicationSecret = Objects.requireNonNull(applicationSecret);
    this.httpClient = Objects.requireNonNull(httpClient);
    this.credentialsStorage = Objects.requireNonNull(credentialsStorage);
    this.httpRequestTimeoutSec = httpRequestTimeoutSec;
  }

  protected final <T> CompletableFuture<T> apiGetObjectRequest(final String apiUri,
      final Map<String, String> parameters,
      final Function<JsonNode, T> dataConverter) {
    return
        apiResourceRequest(HTTP_GET,
            noBody(),
            apiUri,
            Map.of(),
            parameters,
            HTTP_STATUS_OK).thenApply(dataConverter);
  }

  protected final <T> CompletableFuture<T> apiPostObjectRequest(final String apiUri,
      final Map<String, String> parameters,
      final Supplier<String> bodySupplier,
      final Function<JsonNode, T> dataConverter) {
    return
        apiResourceRequest(HTTP_POST,
            BodyPublishers.ofString(bodySupplier.get()),
            apiUri,
            Map.of(),
            parameters,
            HTTP_STATUS_OK).thenApply(dataConverter);
  }

  protected final CompletableFuture<JsonNode> apiResourceRequest(
      final String httpMethod,
      final HttpRequest.BodyPublisher httpRequestBodyPublisher,
      final String apiUrl,
      final Map<String, String> headers,
      final Map<String, String> parameters,
      final int expectedStatus) {

    Function<EwelinkCredentials, CompletableFuture<JsonNode>> requestExecutor = credentials -> {
      Map<String, String> allHeaders = new HashMap<>(
          generateHeaders("Bearer", credentials.getToken()));
      allHeaders.putAll(headers);

      return apiJsonRequest(httpMethod, httpRequestBodyPublisher, apiUrl,
          allHeaders, parameters, expectedStatus);
    };

    var credentials = getCredentials();

    CompletableFuture<JsonNode> requestCompletableFuture = requestExecutor.apply(credentials);
    return requestCompletableFuture.exceptionallyCompose(e -> {
      if (HttpUtils.isEwelinkAuthorizationError(e.getCause())) {
        log.info("Ewelink auth error. {}", e.getMessage());
        log.info("Retry to get new access token");
        return requestExecutor.apply(
            credentialsStorage.relogin(getCredentialsSupplier(), credentials));
      }
      return requestCompletableFuture;
    });
  }

  protected final CompletableFuture<JsonNode> apiJsonRequest(
      final String httpMethod,
      final HttpRequest.BodyPublisher httpRequestBodyPublisher,
      final String apiUrl,
      final Map<String, String> headers,
      final Map<String, String> parameters,
      final int expectedStatus
  ) {
    return
        doRequest(httpMethod, httpRequestBodyPublisher, HttpResponse.BodyHandlers.ofString(),
            apiUrl, headers, parameters, expectedStatus)
            .thenApply(httpResponse -> {
              try {
                JsonNode responseJson = OBJECT_MAPPER.readTree(httpResponse.body());
                log.debug("Received http response: {}", responseJson.toPrettyString());
                checkSuccessResponse(responseJson);
                return responseJson;
              } catch (final JsonProcessingException e) {
                throw new EwelinkClientException(e);
              }
            });
  }

  protected final <T> CompletableFuture<HttpResponse<T>> doRequest(final String httpMethod,
      final HttpRequest.BodyPublisher requestBodyPublisher,
      final HttpResponse.BodyHandler<T> responseBodyHandler,
      final String apiUrl,
      final Map<String, String> headers,
      final Map<String, String> parameters,
      final int expectedStatus) {
    final String apiUrlWithParameters =
        getApiUri(apiUrl) + '?' + parameters.entrySet().stream()
            .map(paramEntry -> paramEntry.getKey() + '=' + paramEntry.getValue())
            .collect(joining("&"));
    final var request =
        HttpRequest.newBuilder(URI.create(apiUrlWithParameters))
            .method(httpMethod, requestBodyPublisher)
            .headers(
                headers.entrySet().stream()
                    .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
                    .toArray(String[]::new))
            .timeout(Duration.ofSeconds(httpRequestTimeoutSec))
            .build();

    log.debug("Request: {}", request);
    log.debug("Request Headers: {}", headers);

    return
        httpClient
            .sendAsync(request, responseBodyHandler)
            .handleAsync((r, t) -> HttpUtils.tryResend(httpClient, request, responseBodyHandler, 1, r, t, expectedStatus))
            .thenCompose(Function.identity());
  }

  private String getApiUri(String apiUri) {
    if (!apiUri.matches("^(https?)://.*$")) {
      return String.format(API_BASE_URI, parameters.getRegion()) + apiUri;
    }
    return apiUri;
  }

  private Map<String, String> generateHeaders(String authSchema, String token) {
    final var nonce = SecurityUtils.generateNonce();
    return Map.of(
        AUTHORIZATION_HEADER, authSchema + " " + token,
        CONTENT_TYPE_HEADER, "application/json",
        ACCEPT_HEADER, "application/json",
        X_CK_NONCE_HEADER, nonce,
        X_CK_APPID_HEADER, applicationId
    );
  }

  private static void checkSuccessResponse(final JsonNode responseJson) {
    var errorNode = responseJson.get("error");
    if (errorNode == null) {
      return;
    }
    var error = errorNode.asInt();
    if (error > 0) {
      String msg = responseJson.get("msg").asText();
      throw new EwelinkApiException(
          String.format(Locale.ROOT, "API responded with error=%d, msg=%s", error, msg), error);
    }
  }

  private Supplier<EwelinkCredentials> getCredentialsSupplier() {
    return () -> {
      String jsonRequestBody = JsonUtils.serialize(
          CredentialsRequest.builder()
              .email(parameters.getEmail())
              .password(parameters.getPassword())
              .countryCode(parameters.getCountryCode())
              .build()
      );
      CompletableFuture<JsonNode> jsonResponse = apiJsonRequest(HTTP_POST,
          BodyPublishers.ofString(jsonRequestBody),
          LOGIN_URI,
          generateHeaders("Sign",
              SecurityUtils.makeAuthorizationSign(applicationSecret, jsonRequestBody)),
          Map.of(),
          HTTP_STATUS_OK);
      return jsonResponse
          .thenApply(JsonUtils.jsonDataConverter(CredentialResponseV2.class))
          .thenApply(
              r -> new EwelinkCredentials(r.getData().getAt(), r.getData().getUser().getApikey()))
          .join();
    };
  }

  protected final EwelinkCredentials getCredentials() {
    return credentialsStorage.get(getCredentialsSupplier());
  }

  protected final void login() {
    credentialsStorage.login(getCredentialsSupplier());
  }
}
