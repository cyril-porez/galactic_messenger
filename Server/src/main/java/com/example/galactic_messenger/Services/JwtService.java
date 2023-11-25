package com.example.galactic_messenger.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

  public String[] verifyToken(String token){
    DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
            .build()
            .verify(token);

    String userId = decodedJWT.getClaim("userId").toString();
    String name = decodedJWT.getSubject();

    String[] dataFromToken = new String[2];
    dataFromToken[0] = userId;
    dataFromToken[1] = name;

    return  dataFromToken;
  }
}
