package com.example.galactic_messenger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.galactic_messenger.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
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
            .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
	          .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/api/user/register").permitAll()
            .requestMatchers("/api/user/login").permitAll()
            .anyRequest().authenticated())
            .headers(headers -> headers.disable());
    
    return http.build();
  }

  
}
