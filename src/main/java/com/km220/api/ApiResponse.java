package com.km220.api;

public class ApiResponse<T> {

  private static final String SUCCESS = "success";
  private static final String ERROR = "error";

  private final String message;
  private final T data;

  public ApiResponse(final String message) {
    this.message = message;
    this.data = null;
  }

  public ApiResponse(final String message, final T data) {
    this.message = message;
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public static ApiResponse<Void> fail() {
    return new ApiResponse<>(ERROR);
  }

  public static ApiResponse<Void> success() {
    return new ApiResponse<>(SUCCESS);
  }
}
