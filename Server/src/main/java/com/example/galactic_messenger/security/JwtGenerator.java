// package com.example.galactic_messenger.security;

// import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
// import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
// import org.springframework.stereotype.Component;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;

// import java.util.Date;

// @Component
// public class JwtGenerator {
//   public String generateToken(Authentication authentication) {
//     String username = authentication.getUsername();
//     Date currentDate = new Date();
//     Date expirDate = new Date(currentDate.getTime() + SecurityConstant.JWT_Expiration);
//     String token = Jwts.builder()
//                       .setSubject(username)
//                       .setIssuedAt(new Date())
//                       .setExpiration(expirDate)
//                       .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SECRET_KEY)
//                       .compact();
//     return token;
//   }

//   public String getUsernameFromJWT(String token) {
//     Claims claims = Jwts.parser()
//                       .setSigningKey(SecurityConstant.JWT_SECRET_KEY)
//                       .parseClaimsJws(token)
//                       .getBody();
//     return claims.getSubject();
//   }

//   public boolean validateToken(String token) {
//     try {
//       Jwts.parser().setSigningKey(SecurityConstant.JWT_SECRET_KEY).parseClaimsJws(token);
//       return true;
//     }
//     catch (Exception ex) {
//       throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
//     }
//   }
// }
