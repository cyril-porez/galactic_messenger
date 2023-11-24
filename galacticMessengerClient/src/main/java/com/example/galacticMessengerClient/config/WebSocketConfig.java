package com.example.galacticMessengerClient.config;

import com.example.galacticMessengerClient.Services.WebSocketService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

@Configuration
public class WebSocketConfig {

  @Bean
  public WebSocketClientBean webSocketClientBean() {
    return new WebSocketClientBean();
  }

  public static class WebSocketClientBean extends StompSessionHandlerAdapter {

    public static class MyStompSessionHandler extends StompSessionHandlerAdapter {

      @Override
      public void afterConnected(StompSession session, @NotNull StompHeaders connectedHeaders) {
        System.out.println("Connected to WebSocket");
        session.subscribe("/topic/messages", new MyStompFrameHandler());
      }
    }
    private static class MyStompFrameHandler implements StompFrameHandler {
      @Override
      public @NotNull Type getPayloadType(@NotNull StompHeaders headers) {
        return String.class;
      }

      @Override
      public void handleFrame(@NotNull StompHeaders headers, Object payload) {
        System.out.println("Received message: " + payload);
      }
    }
  }
}
