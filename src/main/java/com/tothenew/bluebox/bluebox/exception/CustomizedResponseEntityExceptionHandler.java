package com.tothenew.bluebox.bluebox.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler
    extends ResponseEntityExceptionHandler {

  //HTTP STATUS 500 INTERNAL SERVER ERROR
  @ExceptionHandler(Exception.class)
  public final ResponseEntity handleAllExceptions
  (Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  //HTTP STATUS 502 BAD_GATEWAY - Failed to send Mail
  @ExceptionHandler(MailSendFailedException.class)
  public final ResponseEntity<Object> handleMailSendFailedException
  (MailSendFailedException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.BAD_GATEWAY, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_GATEWAY);
  }

  //HTTP STATUS 404 USER NOT FOUND
  @ExceptionHandler(UserNotFoundException.class)
  public final ResponseEntity<Object> handleUserNotFoundExceptions
  (UserNotFoundException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.NOT_FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  //HTTP STATUS 404 TOKEN NOT FOUND
  @ExceptionHandler(TokenNotFoundException.class)
  public final ResponseEntity<Object> handleTokenNotFoundException
  (TokenNotFoundException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.NOT_FOUND, new Date(), "Enter the Valid Token",
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
  }


  //HTTP STATUS 400 BAD REQUEST
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, new Date(),
        "Validation Failed", errors.toString());
    return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  //HTTP STATUS 400 BAD REQUEST
  @ExceptionHandler(CategoryNotFoundException.class)
  public final ResponseEntity<Object> handleCategoryDoesNotExistsException
  (CategoryNotFoundException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.NOT_FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  //HTTP STATUS 400 BAD REQUEST
  @ExceptionHandler(MetadataFieldNotFoundException.class)
  public final ResponseEntity<Object> handleCategoryDoesNotExistsException
  (MetadataFieldNotFoundException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.NOT_FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  //HTTP STATUS 400 BAD REQUEST
  @ExceptionHandler(ProductNotFoundException.class)
  public final ResponseEntity<Object> handleCategoryDoesNotExistsException
  (ProductNotFoundException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.NOT_FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
  }


  //HTTP STATUS 405 METHOD_NOT_ALLOWED
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {

    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED, new Date(),
            "The request method does not support",
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.METHOD_NOT_ALLOWED);
  }

  //HTTP STATUS 302 FOUND - When the user's emailID already Exists
  @ExceptionHandler(UserAlreadyExistsException.class)
  public final ResponseEntity<Object> handleUserAlreadyExistsExceptions
  (UserAlreadyExistsException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.FOUND);
  }

  //HTTP STATUS 302 FOUND - When the user's emailID already Exists
  @ExceptionHandler(CategoryAlreadyExistsException.class)
  public final ResponseEntity<Object> handleCategoryExistsException
  (CategoryAlreadyExistsException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.FOUND);
  }

  //HTTP STATUS 302 FOUND - When the meta Data Field already Exists
  @ExceptionHandler(MetadataFieldExistsException.class)
  public final ResponseEntity<Object> handleMetadataFieldExistsException
  (MetadataFieldExistsException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.FOUND);
  }

  //HTTP STATUS 302 FOUND - When the Category meta Data Field value already Exists
  @ExceptionHandler(CategoryMetadataFieldValueExistsException.class)
  public final ResponseEntity<Object> handleMetadataFieldExistsException
  (CategoryMetadataFieldValueExistsException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.FOUND);
  }

  //HTTP STATUS 302 FOUND - When the Product already Exists
  @ExceptionHandler(ProductAlreadyExistsException.class)
  public final ResponseEntity<Object> handleMetadataFieldExistsException
  (ProductAlreadyExistsException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(HttpStatus.FOUND, new Date(), ex.getMessage(),
            request.getDescription(false));
    return new ResponseEntity(exceptionResponse, HttpStatus.FOUND);
  }
}
