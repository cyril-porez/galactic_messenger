package com.example.galactic_messenger.dto;

public class AuthResponseDTO {
  private String accessToken;
  private String tokenType = "Bearer";

  public AuthResponseDTO(String accessToken) {
    this.accessToken = accessToken;
  }
}
