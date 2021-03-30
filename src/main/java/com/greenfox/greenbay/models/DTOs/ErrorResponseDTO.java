package com.greenfox.greenbay.models.DTOs;

public class ErrorResponseDTO {
  private final String status = "Error";
  private final String message;

  public ErrorResponseDTO(String message) {
    this.message = message;
  }
}
