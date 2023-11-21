package com.example.galactic_messenger.Security;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
  private Long id;
  private String username;
  private String password;
  // private Collection<? extends GrantedAuthority> authorities;


  public MyUserDetails(Long id, String username) {
    this.id = id;
    this.username = username;
    // this.password = password;
    // this.authorities = authorities;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
    public String getPassword() {
        return password;
    }

   // Implémentez les méthodes manquantes ici
   @Override
   public boolean isAccountNonExpired() {
       return true; // ou votre propre logique
   }

   @Override
   public boolean isAccountNonLocked() {
       return true; // ou votre propre logique
   }

   @Override
   public boolean isCredentialsNonExpired() {
       return true; // ou votre propre logique
   }

   @Override
   public boolean isEnabled() {
       return true; // ou votre propre logique
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
       return null; // ou votre propre collection d'autorités
   }




  
}
