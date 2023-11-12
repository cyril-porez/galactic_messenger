package com.example.galacticMessengerClient.controllers;

public class ApiResponse {
  private int status;
  private String message;
  private Object data;

  public ApiResponse () {}

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
  
  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
