package com.km220.ewelink.internal.utils;

import com.km220.ewelink.error.EwelinkApiException;
import com.km220.ewelink.error.EwelinkHttpException;
import com.km220.utils.ExceptionUtils;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class HttpUtils {

  public static final String HTTP_POST = "POST";

  public static final String HTTP_GET = "GET";

  public static final String HTTP_PUT = "PUT";

  public static final String HTTP_DELETE = "DELETE";

  public static final int HTTP_STATUS_OK = 200;

  public static final String CONTENT_TYPE_HEADER = "Content-Type";

  public static final String ACCEPT_HEADER = "Accept";

  public static final String AUTHORIZATION_HEADER = "Authorization";

  public static final String HOST_HEADER = "Host";

  private static final int MAX_RESEND = 10;

  private HttpUtils() {
  }

  public static boolean isEwelinkAuthorizationError(Throwable throwable) {
    if (throwable instanceof EwelinkApiException error) {
      return isAuthErrorCode(error.getCode());
    }
    return false;
  }

  public static boolean isAuthErrorCode(int code) {
    return code == 401 || code == 403 || code == 406;
  }

  public static boolean shouldRetry(HttpResponse<?> response, Throwable error, int count, int expectedStatus) {
    if (count >= MAX_RESEND) {
      return false;
    }
    return error != null || response.statusCode() != expectedStatus;
  }

  public static <T> CompletableFuture<HttpResponse<T>> tryResend(HttpClient client, HttpRequest request,
      BodyHandler<T> handler, int count, HttpResponse<T> response, Throwable error, int expectedStatus) {
    if (shouldRetry(response, error, count, expectedStatus)) {
      log.warn("Retry sending http request..");
      ExceptionUtils.runSafely(() -> Thread.sleep(250));
      return client.sendAsync(request, handler)
          .handleAsync((r, x) -> tryResend(client, request, handler, count + 1, r, x, expectedStatus))
          .thenCompose(Function.identity());
    }

    if (response.statusCode() != expectedStatus) {
      throw new EwelinkHttpException(
          String.format(Locale.ROOT, "Expected status=%d, but API responded with status=%d",
              expectedStatus, response.statusCode()), response.statusCode());
    }

    return CompletableFuture.completedFuture(response);
  }
}
