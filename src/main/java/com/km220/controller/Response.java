package com.km220.controller;

public class Response<T> {

  private static final String SUCCESS = "success";
  private static final String ERROR = "error";

  private final String message;
  private final T data;

  public Response(final String message) {
    this.message = message;
    this.data = null;
  }

  public Response(final String message, final T data) {
    this.message = message;
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public static Response<Void> fail() {
    return new Response<>(ERROR);
  }

  public static Response<Void> success() {
    return new Response<>(SUCCESS);
  }
}
