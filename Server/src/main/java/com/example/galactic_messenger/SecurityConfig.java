package com.example.galactic_messenger;

import com.example.galactic_messenger.security.CustomAuthEntryPoint;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.galactic_messenger.Services.JwtService;
import com.example.galactic_messenger.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
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
            .exceptionHandling((exceptionHandling -> exceptionHandling.authenticationEntryPoint(new CustomAuthEntryPoint(jwtService))))
            .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
	            .authorizeHttpRequests(authorize -> authorize
              .requestMatchers("/h2-console/**").permitAll()
              .requestMatchers("/api/user/register").anonymous()
              .requestMatchers("/api/user/login").anonymous()
              .requestMatchers("/api/user/logout").authenticated()
              .requestMatchers("/api/user/private_chat").authenticated()
              .requestMatchers("/api/user/accept").permitAll()
              .requestMatchers("/api/user/decline").permitAll()
              .requestMatchers("/api/user/online_users").permitAll())
            .headers(headers -> headers.disable());
    
    return http.build();
  }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        };
    }
}
