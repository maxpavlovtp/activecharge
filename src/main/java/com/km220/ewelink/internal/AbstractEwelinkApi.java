package com.km220.ewelink.internal;

import static com.km220.ewelink.internal.utils.HttpUtils.ACCEPT_HEADER;
import static com.km220.ewelink.internal.utils.HttpUtils.AUTHORIZATION_HEADER;
import static com.km220.ewelink.internal.utils.HttpUtils.CONTENT_TYPE_HEADER;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_GET;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_POST;
import static com.km220.ewelink.internal.utils.HttpUtils.HTTP_STATUS_OK;
import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.util.stream.Collectors.joining;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.km220.ewelink.EwelinkClientException;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.utils.SecurityUtils;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEwelinkApi {

  protected final EwelinkParameters parameters;
  protected final String applicationId;
  protected final String applicationSecret;
  protected final HttpClient httpClient;

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final String API_BASE_URI = "https://%s-api.coolkit.cc:8080";
  private static final String LOGIN_URI = "/api/user/login";
  static final int API_VERSION = 8;

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEwelinkApi.class);

  protected AbstractEwelinkApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    this.parameters = parameters;
    this.applicationId = applicationId;
    this.applicationSecret = applicationSecret;
    this.httpClient = httpClient;
  }

  protected final CompletableFuture<CredentialsResponse> getCredentials() {
    String jsonRequestBody = JsonUtils.serialize(
        CredentialsRequest.builder()
            .appid(applicationId)
            .email(parameters.getEmail())
            .password(parameters.getPassword())
            .nonce(SecurityUtils.generateNonce())
            .version(API_VERSION)
            .ts(Instant.now().getEpochSecond())
            .build()
    );
    return apiJsonRequest(HTTP_POST,
        BodyPublishers.ofString(jsonRequestBody),
        LOGIN_URI,
        generateHeaders("Sign",
            SecurityUtils.makeAuthorizationSign(applicationSecret, jsonRequestBody)),
        Map.of(),
        HTTP_STATUS_OK).thenApply(JsonUtils.jsonDataConverter(CredentialsResponse.class));
  }

  protected final <T> CompletableFuture<T> apiGetObjectRequest(final String apiUri,
      final Map<String, String> parameters,
      final Function<JsonNode, T> dataConverter) {
    return
        getCredentials().thenCompose(credentials ->
            apiResourceRequest(HTTP_GET,
                noBody(),
                apiUri,
                Map.of(),
                parameters,
                credentials.getAt(),
                HTTP_STATUS_OK).thenApply(dataConverter));
  }

  protected final <T> CompletableFuture<T> apiPostObjectRequest(final String apiUri,
      final Map<String, String> parameters,
      final Supplier<String> bodySupplier,
      final Function<JsonNode, T> dataConverter) {
    return
        getCredentials().thenCompose(credentials ->
            apiResourceRequest(HTTP_POST,
                BodyPublishers.ofString(bodySupplier.get()),
                apiUri,
                Map.of(),
                parameters,
                credentials.getAt(),
                HTTP_STATUS_OK).thenApply(dataConverter));
  }

  protected final CompletableFuture<JsonNode> apiResourceRequest(
      final String httpMethod,
      final HttpRequest.BodyPublisher httpRequestBodyPublisher,
      final String apiUrl,
      final Map<String, String> headers,
      final Map<String, String> parameters,
      final String accessToken,
      final int expectedStatus) {

    Map<String, String> allHeaders = new HashMap<>(
        generateHeaders("Bearer", accessToken));
    allHeaders.putAll(headers);
    Map<String, String> allParameters = new HashMap<>(generateParameters());
    allParameters.putAll(parameters);

    return apiJsonRequest(httpMethod, httpRequestBodyPublisher, apiUrl, allHeaders,
        allParameters, expectedStatus);
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
            .thenApply(httpResponseBody -> {
              try {
                JsonNode responseJson = OBJECT_MAPPER.readTree(httpResponseBody);
                checkSuccessResponse(responseJson);
                return responseJson;
              } catch (final JsonProcessingException e) {
                throw new EwelinkClientException(e);
              }
            });
  }

  protected final <T> CompletableFuture<T> doRequest(final String httpMethod,
      final HttpRequest.BodyPublisher httpRequestBodyPublisher,
      final HttpResponse.BodyHandler<T> httpResponseBodyHandler,
      final String apiUrl,
      final Map<String, String> headers,
      final Map<String, String> parameters,
      final int expectedStatus) {
    final String apiUrlWithParameters =
        getApiUri(apiUrl) + '?' + parameters.entrySet().stream()
            .map(paramEntry -> paramEntry.getKey() + '=' + paramEntry.getValue())
            .collect(joining("&"));
    final var apiHttpRequest =
        HttpRequest.newBuilder(URI.create(apiUrlWithParameters))
            .method(httpMethod, httpRequestBodyPublisher)
            .headers(
                headers.entrySet().stream()
                    .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
                    .toArray(String[]::new))
            .build();

    LOGGER.debug("Request: {}", apiHttpRequest);
    LOGGER.debug("Request Headers: {}", headers);

    return
        httpClient
            .sendAsync(apiHttpRequest, httpResponseBodyHandler).thenApply(httpResponse -> {
              final int httpStatus = httpResponse.statusCode();
              if (httpStatus != expectedStatus) {
                throw new EwelinkClientException(String.format(Locale.ROOT,
                    "Expected status=%d, but API responded with status=%d", expectedStatus,
                    httpStatus));
              }
              return httpResponse.body();
            });
  }

  private String getApiUri(String apiUri) {
    return String.format(API_BASE_URI, parameters.getRegion()) + apiUri;
  }

  private Map<String, String> generateHeaders(String authSchema, String token) {
    return Map.of(
        AUTHORIZATION_HEADER, authSchema + " " + token,
        CONTENT_TYPE_HEADER, "application/json",
        ACCEPT_HEADER, "application/json"
    );
  }

  private Map<String, String> generateParameters() {
    final var timestamp = Long.toString(Instant.now().getEpochSecond());
    final var nonce = SecurityUtils.generateNonce();
    return Map.of(
        "appid", applicationId,
        "nonce", nonce,
        "ts", timestamp,
        "version", String.valueOf(API_VERSION)
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
      throw new EwelinkClientException(
          String.format(Locale.ROOT, "API responded with error=%d, msg=%s", error, msg));
    }
  }
}
