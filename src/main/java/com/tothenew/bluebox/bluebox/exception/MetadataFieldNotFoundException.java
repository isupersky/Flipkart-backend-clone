package com.tothenew.bluebox.bluebox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MetadataFieldNotFoundException extends RuntimeException {

  public MetadataFieldNotFoundException(String message) {
    super(message);
  }
}
