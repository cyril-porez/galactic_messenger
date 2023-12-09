package com.example.galacticMessengerClient.Services;

import com.example.galacticMessengerClient.config.WebSocketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class WebSocketService {

  @Value("${server.address}")
  private String serverAddress;
  @Value("${server.port")
  private String serverPort;

  public void stompService() throws ExecutionException, InterruptedException {
    String serverUrl = serverAddress + ":" + serverPort + "/ws";

    WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
    stompClient.setMessageConverter(new StringMessageConverter());

    StompSessionHandler sessionHandler = new WebSocketConfig.WebSocketClientBean.MyStompSessionHandler();
    StompSession stompSession = stompClient.connectAsync(serverUrl, sessionHandler).get();

    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("Enter message (or type 'exit' to quit): ");
      String message = scanner.nextLine();

      if ("exit".equalsIgnoreCase(message)) {
        break;
      }
      stompSession.send("/app/chat", message);
    }

    scanner.close();
  }
}

