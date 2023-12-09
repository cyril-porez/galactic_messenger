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

  public void registerUser(String name, String password){
    if (userExists(name)){
      Users user = new Users(name, hashPassword(password));
      repo.save(user);
      repo.flush();
      System.out.println("Bienvenue " + name + " ! Tu est bien inscrit");
    } else {
      System.out.println("Ce nom existe déja");
    }
  }


  public void deletePerson(Long id) {
    repo.deleteById(id);
  }

  public void userlogin(String name, String password){
    Users user = repo.findByName(name);

    if(user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())){
      System.out.println("Bonjour " + name + " vous êtes connecté");
    } else{
      System.out.println("Nom d'utilisateur ou mot de passe incorrect");
    }

  }

  private String hashPassword(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }

  private boolean userExists(String name) {
    if (repo.findByName(name) != null){
      return false;
    }
    return true;
  }

}
