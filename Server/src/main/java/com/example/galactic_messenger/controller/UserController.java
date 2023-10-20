package com.example.galactic_messenger.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.galactic_messenger.Services.Test;

@RequestMapping("/user")
@RestController
public class UserController {

  private Test service;

  public UserController(Test testService){
    this.service = testService;
  }
  // public UserController() {
  //   // Initialisation par défaut, par exemple, vous pouvez instancier un UserRepository ici si nécessaire.
  // }

  @PostMapping("/register")
  public void register(String name, String password){
    this.service.registerUser(name, password);
  }

  @PostMapping("/login")
  public void login(String name, String password){
    this.service.userlogin(name, password);
  }
}
