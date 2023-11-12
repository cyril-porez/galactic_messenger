package com.example.galactic_messenger.controller;

// import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

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

  public ApiResponse sendMessageToAskedUser(String asked_user){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();

    String requestBody = asked_user + " demande à se connecter a vous." + "/accept pour établir une connexion ou /decline pour la refuser.";
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    return null;
  }/*
  public String sendMessage(String asked_user){

  }*/
}
