package com.km220.dao;

public class ChargerDatabaseException extends RuntimeException {

  public ChargerDatabaseException() {
  }

  public ChargerDatabaseException(final String message) {
    super(message);
  }

  public ChargerDatabaseException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ChargerDatabaseException(final Throwable cause) {
    super(cause);
  }

  public ChargerDatabaseException(final String message, final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
