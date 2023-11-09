package com.example.galactic_messenger.Interfaces;

 
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.galactic_messenger.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
  Optional<Users> findByName(String name);
  Boolean existsByName(String name);
}
