package com.km220.controller;

import com.km220.service.job.DuplicateChargingException;
import com.km220.service.job.NoStationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ChargerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ChargerResponse<String>> fallback(Exception exception,
      WebRequest request) {
    logger.error("Error.", exception);

    var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    if (exception instanceof DuplicateChargingException) {
      httpStatus = HttpStatus.CONFLICT;
    }

    if (exception instanceof NoStationException) {
      httpStatus = HttpStatus.NOT_FOUND;
    }

    return new ResponseEntity<>(ChargerResponse.fail(exception.getMessage()), httpStatus);
  }
}
