package com.example.galactic_messenger.Interfaces;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.galactic_messenger.model.Groups;


@Repository
public interface GroupRepository extends JpaRepository<Groups, Long>{
  Groups findByName(String name);
}
