package com.example.galactic_messenger.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import com.example.galactic_messenger.Interfaces.UserRepository;
import com.example.galactic_messenger.model.Roles;
import com.example.galactic_messenger.model.Users;


@Service
public class CustomUserDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
@Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRoleAuthorities(user.getRoles()));
  }

  private Collection<GrantedAuthority> mapRoleAuthorities(List<Roles> roles) {    
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
  }
  
  
}
