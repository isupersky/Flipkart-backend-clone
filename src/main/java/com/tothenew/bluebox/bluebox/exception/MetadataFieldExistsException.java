package com.tothenew.bluebox.bluebox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class MetadataFieldExistsException extends RuntimeException {

  public MetadataFieldExistsException(String message) {
    super(message);
  }
}
