package com.example.galactic_messenger.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.galactic_messenger.model.Roles;
import java.util.Optional;


public interface RoleRepository extends JpaRepository<Roles, Long> {
  Optional<Roles> findByName(String name);
  
} 