package com.example.galactic_messenger.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private CustomUserDetailsService userDetailsService;

  private JwtAuthEntryPoint authEntryPoint;

  public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint) {
    this.userDetailsService = userDetailsService;
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
              .requestMatchers("/api/auth/register").permitAll()
              .requestMatchers("api/user/register").permitAll()
              .requestMatchers("api/user/login").permitAll()
              .requestMatchers(HttpMethod.GET).authenticated()
              .anyRequest().authenticated())
            // .httpBasic(httpBasic -> httpBasic
            //   .authenticationEntryPoint(authEntryPoint))
            .headers(headers -> headers.disable());
    //  http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    
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
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean 
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // @Bean
  // public JWTAuthenticationFilter jwtAuthenticationFilter() {
  //   return new JWTAuthenticationFilter();
  // }
}
