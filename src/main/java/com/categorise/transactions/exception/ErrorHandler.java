package com.categorise.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(value = ApplicationException.class)
  public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
    ErrorResponse errorResponse = ErrorResponse.builder().message(e.getMessage()).build();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
}
