package com.example.galactic_messenger.Services;

import java.util.Scanner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.galactic_messenger.controller.HelpTest;


public class ServerCommand {

  public ServerCommand() {

  }

  public void Command() {
    Scanner scanner = new Scanner(System.in);
    
		System.out.print("Veuiller entrer une commande: ");
		
    boolean isRunning = true;
		
    while (isRunning) {
      String comand = scanner.nextLine();
		  String[] comandSplit = comand.split(" ");
		  String choiceComand = comandSplit[0];
      switch (choiceComand) {
        case "/help":
          HelpTest help = new HelpTest();
          System.out.println(help.CommandeHelp());				
        break;
        case "/register":
          handleRegister(comandSplit[1], comandSplit[2]);
          System.out.println(comandSplit[1]);
          System.out.println(comandSplit[2]);
          System.out.println("tu es inscrits");
          break;
        case "/login":
          handleLogin(comandSplit[1], comandSplit[2]);
          System.out.println("tu es connecté");
          break;
        case "/quit":
          isRunning = false;
          break;
        default:
          System.out.println("Commande inconnue");
          break;
      }
      
    }
		scanner.close();

  }

  public void handleRegister(String name, String password){
    request(name, password, "register");
  }

  public void handleLogin(String name, String password){
    request(name, password, "login");
  }

  public void request(String name, String password, String urlArgs){
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();

    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("name", name);
    map.add("password", password);

    HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map, httpHeaders);
    String url = String.format("http://localhost:8082/user/%s", urlArgs);
    restTemplate.postForObject(url, req, Void.class, map);
  }
}
