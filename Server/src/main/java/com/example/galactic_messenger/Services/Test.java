package com.example.galactic_messenger.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.model.Users;

@Service
public class Test {

  private UserRepository repo;


  @Autowired
  public Test(UserRepository repository){
    this.repo = repository;
  }
  public Test() {

  }

  public String registerUser(String name, String password){
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
  }


  public void deletePerson(Long id) {
    repo.deleteById(id);
  }

  public String userlogin(String name, String password){
    Users user = repo.findByName(name).orElse(null);
    String str = "";
    if(user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())){
      str = "Vous êtes connectées !" ;
    } else{
      str = "Nom d'utilisateur ou mot de passe incorrect";
    }
    return str;
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
