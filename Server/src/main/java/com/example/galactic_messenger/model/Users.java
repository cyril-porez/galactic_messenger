package com.example.galactic_messenger.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;


@Entity
public class Users {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  public String name;
  public String password;
  
  private boolean isConnected;
  

  public Users(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public Users() {

  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password){
    this.password = password;
  }
  
  public boolean getIsConnected() {
    return this.isConnected;
  }

  public void setIsConnected(boolean isConnected) {
    this.isConnected = isConnected;
  }
}
