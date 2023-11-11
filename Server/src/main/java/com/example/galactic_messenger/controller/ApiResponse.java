package com.example.galactic_messenger.controller;

// import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.io.IOException;

public class ApiResponse {
  private String status;
  private String message;
  private String data;

  public ApiResponse () {}

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
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
