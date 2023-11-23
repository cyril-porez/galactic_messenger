package com.example.galactic_messenger.security;

import java.io.IOException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    String token = request.getParameter("token");
    System.out.println(token);


    if (token != null && token.startsWith("Bearer ")) {
      try {
        token = token.substring(7);
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
          .build()
          .verify(token);

        String userId = decodedJWT.getClaim("userId").asString();
        System.out.println(userId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userId,
                null,
                null
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
      } catch (JWTVerificationException e) {
        throw new ServletException("Token JWT invalid");
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }

  public String getToken(ServletRequest request){
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String authorizationHeader = httpServletRequest.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
      return authorizationHeader.substring(7);
    }

    return null;
  }
}
