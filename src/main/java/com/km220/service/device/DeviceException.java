package com.km220.service.device;

public class DeviceException extends RuntimeException {

  public DeviceException(final String message) {
    super(message);
  }

  public DeviceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public DeviceException(final Throwable cause) {
    super(cause);
  }

  public DeviceException(final String message, final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
