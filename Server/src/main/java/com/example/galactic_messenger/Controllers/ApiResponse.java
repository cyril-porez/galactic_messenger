package com.example.galactic_messenger.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.io.IOException;

public class ApiResponse {
  private int status;
  private String message;
  private String data;

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
  
  public String getData() {
    return data;
  }

  public void setData(Map<String, Object> dataMap) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    this.data = mapper.writeValueAsString(dataMap);
  }

}
