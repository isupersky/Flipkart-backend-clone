package com.tothenew.bluebox.bluebox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class MailSendFailedException extends RuntimeException {

  public MailSendFailedException(String message) {
    super(message);
  }
}
