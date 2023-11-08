package com.example.galactic_messenger.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private JwtAuthEntryPoint authEntryPoint;

  public SecurityConfig(JwtAuthEntryPoint authEntryPoint) {
    this.authEntryPoint = authEntryPoint;

  }
  
  /**
   * @param http
   * @return
   * @throws Exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement -> sessionManagement
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .sessionFixation().migrateSession()
              .maximumSessions(1).maxSessionsPreventsLogin(true))
	          .authorizeHttpRequests(authorize -> authorize            
              .requestMatchers("/h2-console/**").permitAll()
              .requestMatchers("api/user/register").permitAll()
              .requestMatchers("api/user/login").permitAll()
              .anyRequest().authenticated())
            .httpBasic(httpBasic -> httpBasic
              .authenticationEntryPoint(authEntryPoint))
            .headers(headers -> headers.disable());
     http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }

  @Bean
  public UserDetailsService users() {
    UserDetails admin = User.builder()
      .username("admin")
      .password("password")
      .roles("ADMIN")
      .build();

      UserDetails user = User.builder()
      .username("test")
      .password("test")
      .roles("USER")
      .build();

    return new InMemoryUserDetailsManager(admin, user);
  }

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }
}