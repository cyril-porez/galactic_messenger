package com.example.galacticMessengerClient.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.stomp.*;


import java.lang.reflect.Type;

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
        System.out.println("Connected to WebSocket Server");
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
