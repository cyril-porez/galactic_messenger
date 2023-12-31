package com.example.galactic_messenger.Interfaces;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.galactic_messenger.dto.Users;



@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
  Users findByName(String name);
}
