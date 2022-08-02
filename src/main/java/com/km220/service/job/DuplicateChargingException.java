package com.km220.service.job;

public class DuplicateChargingException extends RuntimeException {

  public DuplicateChargingException(final String message) {
    super(message);
  }
}
