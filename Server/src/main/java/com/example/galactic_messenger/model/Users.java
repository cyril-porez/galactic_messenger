package com.example.galactic_messenger.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

  @ManyToMany(fetch =  FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
  private List<Roles> roles = new ArrayList<>();

  public List<Roles> getRoles() {
    return roles;
  }

  public void setRoles(List<Roles> roles) {
    this.roles = roles;
}
  
}
