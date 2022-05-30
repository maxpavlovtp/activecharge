package com.km220.ewelink;

public class EwelinkApiException extends RuntimeException {

  public EwelinkApiException(final Throwable cause) {
    super(cause);
  }

  public EwelinkApiException(final String message) {
    super(message);
  }
}
