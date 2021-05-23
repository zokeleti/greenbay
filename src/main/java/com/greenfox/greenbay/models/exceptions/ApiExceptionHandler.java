package com.greenfox.greenbay.models.exceptions;

import com.greenfox.greenbay.models.DTOs.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(value = {
      MissingFieldException.class,
      InvalidPriceException.class,
      InvalidURLException.class,
      ItemNotFoundException.class
  })
  public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(Exception e) {
    ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());
    return ResponseEntity.status(400).body(response);
  }

  /*@ExceptionHandler(value = InvalidPriceException.class)
  public ResponseEntity<ErrorResponseDTO> handleInvalidPriceException(InvalidPriceException e) {
    return ResponseEntity.unprocessableEntity().body(new ErrorResponseDTO(e.getMessage()));
  }

  @ExceptionHandler(value = InvalidURLException.class)
  public ResponseEntity<ErrorResponseDTO> handelInvalidURLException(InvalidURLException e) {
    return ResponseEntity.unprocessableEntity().body(new ErrorResponseDTO(e.getMessage()));
  }*/
}
