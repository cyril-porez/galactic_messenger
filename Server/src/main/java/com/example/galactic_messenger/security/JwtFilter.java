package com.example.galactic_messenger.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
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
    String token = getTokenFromRequest(httpServletRequest);


    if (token != null && token.startsWith("Bearer ")) {
      try {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);

        String userId = decodedJWT.getClaim("userId").asString();
        String name = decodedJWT.getSubject();
        System.out.println(userId + "\t" + name);
        setAuth(name);
        filterChain.doFilter(request, response);
      } catch (JWTVerificationException e) {
        throw new ServletException("Token JWT invalid");
      }
    } else {
      setAnonymousAuthentication();
      filterChain.doFilter(request, response);
    }
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public static void setAuth(String userId) {
    // Create a simple Authentication object with only the principal (user's ID)
    Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null
            , Collections.singletonList(new SimpleGrantedAuthority("Role_User")));

    // Set the Authentication object in the SecurityContextHolder
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);
  }

  public static void setAnonymousAuthentication() {
    // Create an AnonymousAuthenticationToken
    Authentication anonymousAuthentication = new AnonymousAuthenticationToken(
            "key",             // a key for the anonymous user (can be any unique value)
            "anonymousUser",   // principal (typically a string identifying the anonymous user)
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
    );

    // Set the Authentication object in the SecurityContextHolder
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(anonymousAuthentication);
  }
}
