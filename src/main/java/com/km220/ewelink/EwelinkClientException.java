package com.km220.ewelink;

public class EwelinkClientException extends RuntimeException {

  public EwelinkClientException(final Throwable cause) {
    super(cause);
  }

  public EwelinkClientException(final String message) {
    super(message);
  }
}
