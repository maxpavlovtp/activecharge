package com.km220.ewelink;

public class EwelinkApiException extends EwelinkClientException {

  private final int code;

  public EwelinkApiException(final Throwable cause, final int code) {
    super(cause);
    this.code = code;
  }

  public EwelinkApiException(final String message, final int code) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
