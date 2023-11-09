package com.example.galactic_messenger.controller;

import java.util.Collections;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.galactic_messenger.Interfaces.RoleRepository;
import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.dto.RegisterDto;
import com.example.galactic_messenger.model.Roles;
import com.example.galactic_messenger.model.Users;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private AuthenticationManager authenticationManager;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  
  public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
  RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @PostMapping("/register")
  public ResponseEntity<String>register(@RequestBody RegisterDto registerDto){
    if (userRepository.existsByName(registerDto.getName())) {
      return new ResponseEntity<>("Les identifiants sont pris", HttpStatus.BAD_REQUEST);
    }
    Users users = new Users();
    users.setName(registerDto.getName());
    users.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    Roles roles = roleRepository.findByName("USER").get();
    users.setRoles(Collections.singletonList(roles));
    userRepository.save(users);
    return new ResponseEntity<>("Utilisteur inscrits", HttpStatus.OK);
  }
}
