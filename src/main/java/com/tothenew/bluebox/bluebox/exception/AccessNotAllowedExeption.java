package com.tothenew.bluebox.bluebox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class AccessNotAllowedExeption extends RuntimeException {

  public AccessNotAllowedExeption(String message) {
    super(message);
  }
}
