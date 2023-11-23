package com.example.galacticMessengerClient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Configuration
public class WebSocketConfig {
  
  @Bean
  public WebSocketStompClient stompClient() {
    WebSocketClient clent = new StandardWebSocketClient();
    WebSocketStompClient stompClient = new WebSocketStompClient(clent);
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    return stompClient;
  }
}
