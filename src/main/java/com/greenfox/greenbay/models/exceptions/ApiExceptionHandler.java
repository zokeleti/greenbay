package com.greenfox.greenbay.models.exceptions;

import com.greenfox.greenbay.models.DTOs.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(value = MissingFieldException.class)
  public ResponseEntity<ErrorResponseDTO> handleMissingFieldException(MissingFieldException e) {
    return ResponseEntity.unprocessableEntity().body(new ErrorResponseDTO(e.getMessage()));
  }

  @ExceptionHandler(value = InvalidPriceException.class)
  public ResponseEntity<ErrorResponseDTO> handleInvalidPriceException(InvalidPriceException e) {
    return ResponseEntity.unprocessableEntity().body(new ErrorResponseDTO(e.getMessage()));
  }

  @ExceptionHandler(value = InvalidURLException.class)
  public ResponseEntity<ErrorResponseDTO> handelInvalidURLException(InvalidURLException e) {
    return ResponseEntity.unprocessableEntity().body(new ErrorResponseDTO(e.getMessage()));
  }
}
