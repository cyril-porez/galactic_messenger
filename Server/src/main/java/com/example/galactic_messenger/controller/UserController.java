package com.example.galactic_messenger.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.galactic_messenger.Services.Test;
// import com.example.galactic_messenger.security.JwtGenerator;
import com.example.galactic_messenger.model.Users;

@RequestMapping("/api/user")
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
  public CompletableFuture<ResponseEntity<ApiResponse>> register(@RequestParam String name, @RequestParam String password) {
    return service.registerUser(name, password)
            .handle((result, ex) -> {
                ApiResponse response = new ApiResponse();

                if (result.equals("Inscription réussie")) {
                    response.setStatus("success");
                    response.setMessage("Vous êtes inscrits");
                    Users user = new Users(name, password);
                    response.setData(user);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else if (result.equals("Ce nom existe déjà")) {
                    response.setStatus("error");
                    response.setMessage("Les identifiants sont incorects");
                    response.setData(null);
                    System.out.println(response);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                } else {
                    response.setStatus("test");
                    response.setMessage("Ces identifiants n'existent pas");
                    response.setData("test");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                }
            });
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse> login(@RequestParam String name, @RequestParam String password){
    String result = service.userlogin(name, password); 
    ApiResponse response = new ApiResponse();
    if (result.equals("Vous êtes connectées !")){
      response.setStatus("sucess");
      response.setMessage("Vous vous êtes connecté avec succès !");
      response.setData(name);
      // Authentication authentication = authenticationManager();
      // String token = JwtGenerator.generateToken(); 
      return ResponseEntity.ok(response); 
    } else if (result.equals("Nom d'utilisateur ou mot de passe incorrect")) {
      response.setStatus("error");
      response.setMessage("Les identifiants sont incorects");
      response.setData(null);  
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);      
    } else {
      response.setStatus("test");
      response.setMessage("Ces identifiants n'existent pas ");
      response.setData("test");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }
}
