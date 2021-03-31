package com.greenfox.greenbay.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDTO {
  private final String status = "Authenticated";
  private String token;

  public LoginResponseDTO(String token) {
    this.token = token;
  }
}
