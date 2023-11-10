package com.example.galactic_messenger.controller;

import org.json.JSONObject;
import java.util.*;

public class ApiResponse {
  private String status;
  private String message;
  private Map<String, Object> data;

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
  
  public Map<String, Object> getData() {
    return data;
  }

  public void setData(JSONObject data) {
    this.data = new HashMap<String, Object>();
    for(String key : JSONObject.getNames(data)) {
        this.data.put(key, data.get(key));
    }
  }
}
