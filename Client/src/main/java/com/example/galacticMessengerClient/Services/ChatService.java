package com.example.galacticMessengerClient.Services;

import org.springframework.messaging.simp.stomp.StompSession;

public class ChatService {

  private final StompSession stompSession;

  public ChatService(StompSession stompSession) {
    this.stompSession = stompSession;
  }
  

  public void sendPrivateChat(String receiver) {
    
  }
}
