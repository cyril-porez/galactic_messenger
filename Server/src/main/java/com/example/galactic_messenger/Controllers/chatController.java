package com.example.galactic_messenger.Controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.galactic_messenger.Services.ChatService;

@RequestMapping("/api/chat")
@RestController
public class chatController {

  private ChatService chatService;

  public chatController() {
     chatService = new ChatService();
  }
 

  @PostMapping("/privateChat")
  public void handlePrivateChat() {    
    chatService.handlePrivateChat();
  }

  @MessageMapping("/accept") 
  public void acceptChat() {
    chatService.acceptPrivateChat();
  }

  @PostMapping("/decline")
  public void declineChat() {
    chatService.declinePrivateChat();
  }

  // @MessageMapping("/chat.sendMessage")
  // public String sendMessage(String chatMessage){
  //   return chatMessage;
  // }

  // @MessageMapping("/chat.newUser")
  // @SendTo("/topic/public")
  // public String addUserChatMessage(String username) {
  //   return "Nouvel utilisateur" + username;
  // }

}
