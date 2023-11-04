package com.example.galacticMessengerClient.Request;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RequestApi {

  public RequestApi() {

  }

  public void request(String name, String password, String addressIp, String command){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();

    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("name", name);
    map.add("password", password);

    HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map, httpHeaders);
    String url = String.format("http://%s/api/user/%s",addressIp, command);
    restTemplate.postForObject(url, req, Void.class, map);
	};
  
}
