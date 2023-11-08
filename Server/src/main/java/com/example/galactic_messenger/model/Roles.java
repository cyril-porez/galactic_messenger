package com.example.galactic_messenger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Roles")
public class Roles {
  @Id
  @GeneratedValue
  private long id;

  private String name;
}
