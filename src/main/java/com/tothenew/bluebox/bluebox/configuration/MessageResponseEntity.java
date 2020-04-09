package com.tothenew.bluebox.bluebox.configuration;

import org.springframework.http.HttpStatus;

public class MessageResponseEntity<T> {

  private HttpStatus statusCode;
  private String message;
  private T data;
  private boolean status = true;

  public MessageResponseEntity(T data) {
    this(data, HttpStatus.OK);
  }

  public MessageResponseEntity(T data, HttpStatus statusCode) {
    this(statusCode, "SUCCESS");
    this.data = data;
  }

  public MessageResponseEntity(T data, HttpStatus statusCode, String message) {
    this(statusCode, message);
    this.data = data;
  }

  public MessageResponseEntity(HttpStatus statusCode, String message) {
    if (message == null) {
      message = "SOMETHING WENT WRONG";
    }
    this.statusCode = statusCode;
    this.message = message;
  }

  public MessageResponseEntity(HttpStatus statusCode, String message, Boolean status) {
    this(statusCode, message);
    this.status = status;
  }

  public HttpStatus getStatusCode() {
    return statusCode;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public boolean isStatus() {
    return status;
  }
}