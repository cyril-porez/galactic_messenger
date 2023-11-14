package com.example.galactic_messenger.Interfaces;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.galactic_messenger.model.Users;

import java.util.LinkedList;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{
  Users findByName(String name);
  /*Users findAllByConnectedIsTrue();*/
  /*LinkedList<Users> findAll();*/
}
