package com.example.galactic_messenger.security;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class JwtFilter extends GenericFilterBean {
  private final String secretKey = "sldfdnsdldsj";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String token = httpServletRequest.getHeader("Authorization");

    if (token != null && token.startsWith("Bearer ")) {
      try {
        token = token.substring(7);
        JWT.require(Algorithm.HMAC256(secretKey))
          .build()
          .verify(token);
        filterChain.doFilter(request, response);
      } catch (JWTVerificationException e) {
        throw new ServletException("Token JWT invalid");
      }
    } else {
      filterChain.doFilter(request, response);
    }   
  }  
}
