package com.greenfox.greenbay.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDTO {
  private final String status = "Error";
  private String message;

  public ErrorResponseDTO(String message) {
    this.message = message;
  }
}
