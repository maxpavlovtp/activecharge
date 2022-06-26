package com.km220.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ChargerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ChargerResponse<String>> fallback(Exception exception, WebRequest request) {
    //TODO: change status code
    return new ResponseEntity<>(ChargerResponse.fail(exception.getMessage()), HttpStatus.OK);
  }
}
