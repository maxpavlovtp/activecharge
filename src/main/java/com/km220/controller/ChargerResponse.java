package com.km220.controller;

public class ChargerResponse<T> {

  private static final String SUCCESS = "success";
  private static final String ERROR = "error";

  private String message;
  private T data;

  public ChargerResponse() {
  }

  public ChargerResponse(final String message) {
    this.message = message;
    this.data = null;
  }

  public ChargerResponse(final String message, final T data) {
    this.message = message;
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public boolean isSuccess() {
    return SUCCESS.equals(message);
  }

  public static ChargerResponse<String> fail(String reason) {
    return new ChargerResponse<>(ERROR, reason);
  }

  public static ChargerResponse<Void> success() {
    return new ChargerResponse<>(SUCCESS);
  }

  public static <T> ChargerResponse<T> success(T data) {
    return new ChargerResponse<>(SUCCESS, data);
  }
}
