package com.km220.ewelink.internal.v2;

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
import com.km220.ewelink.EwelinkApiException;
import com.km220.ewelink.EwelinkParameters;
import com.km220.ewelink.internal.CredentialsRequest;
import com.km220.ewelink.internal.utils.JsonUtils;
import com.km220.ewelink.internal.utils.SecurityUtils;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEwelinkApi2 {

  protected final EwelinkParameters parameters;
  protected final String applicationId;
  protected final String applicationSecret;
  protected final HttpClient httpClient;

  private static final String API_BASE_URI = "https://%s-apia.coolkit.cc/v2";
  private static final String LOGIN_URI = "/user/login";

  private static final String X_CK_NONCE_HEADER = "X-CK-Nonce";
  private static final String X_CK_APPID_HEADER = "X-CK-Appid";

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEwelinkApi2.class);

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final Map<String, String> tokenCache = new ConcurrentHashMap<>();

  protected AbstractEwelinkApi2(final EwelinkParameters parameters, final String applicationId,
      final String applicationSecret, final HttpClient httpClient) {
    this.parameters = Objects.requireNonNull(parameters);
    this.applicationId = Objects.requireNonNull(applicationId);
    this.applicationSecret = Objects.requireNonNull(applicationSecret);
    this.httpClient = Objects.requireNonNull(httpClient);
  }

  protected final String getAccessToken() {
    Supplier<CompletableFuture<String>> tokenExtractor = () -> {
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
          .thenApply(r -> r.getData().getAt());
    };

    return tokenCache.computeIfAbsent("token", key -> tokenExtractor.get().join());
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
            getAccessToken(),
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
            getAccessToken(),
            HTTP_STATUS_OK).thenApply(dataConverter);
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

    return apiJsonRequest(httpMethod, httpRequestBodyPublisher, apiUrl, allHeaders,
        parameters, expectedStatus);
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
                throw new EwelinkApiException(e);
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
                throw new EwelinkApiException(String.format(Locale.ROOT,
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
          String.format(Locale.ROOT, "API responded with error=%d, msg=%s", error, msg));
    }
  }
}
