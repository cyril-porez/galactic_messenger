package com.example.galactic_messenger.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.galactic_messenger.security.MyUserDetails;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final String secretKey = "sldfdnsdldsj";

  public String generateToken(MyUserDetails userDetails) {
    long expirationTime = 1000 * 60 * 60;

    return JWT.create()
      .withSubject(userDetails.getUsername())
      .withClaim("userId", userDetails.getId())
      .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
      .sign(Algorithm.HMAC256(secretKey));
  }  
}
