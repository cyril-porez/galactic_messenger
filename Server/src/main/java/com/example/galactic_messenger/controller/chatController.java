package com.example.galactic_messenger.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class chatController {
  
  @MessageMapping("/chat.sendMessage")
  public String sendMessage(String chatMessage){
    return chatMessage;
  }

  @MessageMapping("/chat.newUser")
  @SendTo("/topic/public")
  public String addUserChatMessage(String username) {
    return "Nouvel utilisateur" + username;
  }
}
