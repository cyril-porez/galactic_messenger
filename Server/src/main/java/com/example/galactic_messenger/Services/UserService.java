package com.example.galactic_messenger.Services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.dto.Users;

@Service
public class UserService {

  private UserRepository repo;


  @Autowired
  public UserService(UserRepository repository){
    this.repo = repository;
  }
  public UserService() {

  }

  @Async
  public CompletableFuture<String> registerUser(String name, String password){
    return CompletableFuture.supplyAsync(() -> {
      String str = "";
      if (userExists(name)){
        str = "Ce nom existe déjà";
        System.out.println("user exist");
      } else { 
        Users user = new Users(name, hashPassword(password));
        repo.save(user);
        repo.flush();
        str = "Inscription réussie";
        System.out.println("user création");
      }
      return str;
    });
  }

  public void deletePerson(Long id) {
    repo.deleteById(id);
  }

  @Async
  public CompletableFuture<String> userlogin(String name, String password){
    return CompletableFuture.supplyAsync(() -> {
      Users user = repo.findByName(name);
      String str = "";
      if(user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())){
        str = "Vous êtes connectés !" ;
      } else{
        str = "Nom d'utilisateur ou mot de passe incorrect";
      }
      return str;
    });
  }

  private String hashPassword(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }

  private boolean userExists(String name) {
    boolean isFind = true;
    if (repo.findByName(name) == null){
      isFind = false;
    }
    return isFind;
  }

}
