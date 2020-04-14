package com.tothenew.bluebox.bluebox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryDoesNotExistsException extends RuntimeException {

  public CategoryDoesNotExistsException(String message) {
    super(message);
  }
}
