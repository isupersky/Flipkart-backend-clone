package com.tothenew.bluebox.bluebox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductVariationNotFoundException extends RuntimeException {

  public ProductVariationNotFoundException(String message) {
    super(message);
  }
}
