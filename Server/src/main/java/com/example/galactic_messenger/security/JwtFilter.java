package com.example.galactic_messenger.security;

import java.io.IOException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.galactic_messenger.Services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {
  private final String secretKey = "sldfdnsdldsj";
  private final JwtService jwtService;

  public JwtFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
          throws ServletException, IOException {

    String token = getTokenFromRequest(request);

    if (token != null) { // vérifie le token et extrait l'id et l'username
      String[] data = jwtService.verifyToken(token);

      MyUserDetails userDetails = new MyUserDetails(Long.parseLong(data[0]), data[1]);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
              null);

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      filterChain.doFilter(request, response);
    }
    else{
      filterChain.doFilter(request, response);
    }
  }
  public String getTokenFromRequest(HttpServletRequest request){

    // Obtient le token depuis le header http
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
      return authorizationHeader.substring(7);
    }

    return null;
  }

}
