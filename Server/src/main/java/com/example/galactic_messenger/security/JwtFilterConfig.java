package com.example.galactic_messenger.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;

import com.example.galactic_messenger.Services.JwtService;

@Configuration
public class JwtFilterConfig {
  private final JwtService jwtService;

  public JwtFilterConfig(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  public FilterRegistrationBean<JwtFilter> jwtFilter() {
    FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new JwtFilter(jwtService));
    registrationBean.addUrlPatterns("/api/*");
    return registrationBean;
  }
}
