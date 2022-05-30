package com.km220.ewelink;

import static com.km220.ewelink.internal.HttpUtils.AUTHORIZATION_HEADER;
import static com.km220.ewelink.internal.HttpUtils.CONTENT_TYPE_HEADER;
import static com.km220.ewelink.internal.HttpUtils.HTTP_GET;
import static com.km220.ewelink.internal.HttpUtils.HTTP_STATUS_OK;
import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.util.stream.Collectors.joining;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class AbstractEwelinkApi {

  protected final EwelinkParameters parameters;
  private final String applicationId;
  private final String applicationSecret;
  private final HttpClient httpClient;

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final String API_BASE_URI = "https://%s-api.coolkit.cc:8080/api";
  private static final String API_VERSION = "8";

  protected AbstractEwelinkApi(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    this.parameters = parameters;
    this.applicationId = applicationId;
    this.applicationSecret = applicationSecret;
    this.httpClient = httpClient;
  }

  protected final <T> CompletableFuture<T> apiGetObjectRequest(final String apiUrl,
      final Map<String, String> parameters,
      final Function<JsonNode, T> dataConverter) {
    return
        apiJsonRequest(HTTP_GET, noBody(), apiUrl, Map.of(), parameters, HTTP_STATUS_OK)
            .thenApply(dataConverter);
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
        apiRequest(httpMethod, httpRequestBodyPublisher, HttpResponse.BodyHandlers.ofString(),
            apiUrl, headers, parameters, expectedStatus)
            .thenApply(httpResponseBody -> {
              try {
                JsonNode responseJson = OBJECT_MAPPER.readTree(httpResponseBody);
                checkSuccessResponse(responseJson);
                return responseJson;
              } catch (final JsonProcessingException e) {
                throw new EwelinkApiException(e);
              }
            });
  }

  protected final <T> CompletableFuture<T> apiRequest(
      final String httpMethod,
      final HttpRequest.BodyPublisher httpRequestBodyPublisher,
      final HttpResponse.BodyHandler<T> httpResponseBodyHandler,
      final String apiUrl,
      final Map<String, String> headers,
      final Map<String, String> parameters,
      final int expectedStatus
  ) {
    final String apiUrlWithParameters =
        getApiUri(apiUrl) + '?' + Stream
            .concat(parameters.entrySet().stream(), generateParameters().entrySet().stream())
            .map(paramEntry -> paramEntry.getKey() + '=' + paramEntry.getValue())
            .collect(joining("&"));
    final var apiHttpRequest =
        HttpRequest.newBuilder(URI.create(apiUrlWithParameters))
            .method(httpMethod, httpRequestBodyPublisher)
            .headers(
                Stream.concat(headers.entrySet().stream(), generateHeaders().entrySet().stream())
                    .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
                    .toArray(String[]::new))
            .build();
    return
        httpClient
            .sendAsync(apiHttpRequest, httpResponseBodyHandler)
            .thenApply(httpResponse -> {
              final int httpStatus = httpResponse.statusCode();
              if (httpStatus != expectedStatus) {
                throw new EwelinkApiException(String.format(Locale.ROOT,
                    "Expected status=%d, but API responded with status=%d", expectedStatus,
                    httpStatus));
              }
              return httpResponse.body();
            });
  }

  private String getApiUri(String apiUri) {
    return String.format(API_BASE_URI, parameters.region()) + apiUri;
  }

  private Map<String, String> generateHeaders() {
    return Map.of(
        AUTHORIZATION_HEADER, "Bearer " + applicationSecret,
        CONTENT_TYPE_HEADER, "application/json"
    );
  }

  private Map<String, String> generateParameters() {
    final var timestamp = Long.toString(Instant.now().getEpochSecond());
    return Map.of(
        "appid", applicationId,
        "nonce", "",
        "ts", timestamp,
        "version", API_VERSION
    );
  }

  private static void checkSuccessResponse(final JsonNode responseJson) {
    final var error = responseJson.get("error").asInt();
    if (error > 0) {
      String msg = responseJson.get("msg").asText();
      throw new EwelinkApiException(
          String.format(Locale.ROOT, "API responded with error=%d, msg=%s", error, msg));
    }
  }

  protected static <T> Function<JsonNode, T> jsonDataConverter(Class<T> clazz) {
    return jsonData -> {
      try {
        return OBJECT_MAPPER.treeToValue(jsonData, clazz);
      } catch (JsonProcessingException e) {
        throw new EwelinkApiException(e);
      }
    };
  }
}
