package com.tothenew.bluebox.bluebox.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;

public class ExceptionResponse {

  private HttpStatus statusCode;
  private Date timestamp;
  private String message;
  private String details;

  public ExceptionResponse(HttpStatus statusCode, Date timestamp, String message,
      String details) {
    super();
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  public String getDetails() {
    return details;
  }

}
