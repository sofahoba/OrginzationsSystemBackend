package com.orgnization.orgnizationSystemdemo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.orgnization.orgnizationSystemdemo.config.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtUtill {
  
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long jwtExpiration;

  public String generateToken(CustomUserDetails userDetails){
    Map<String,Object>claims=new HashMap<>();
    var roles=userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();
    claims.put("roles",roles);
    return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
  }
  public String generateRefreshToken(CustomUserDetails userDetails) {
      Map<String, Object> claims = new HashMap<>();
      var roles = userDetails.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .toList();
      claims.put("roles", roles);

      long refreshExpirationMs = jwtExpiration * 7;   

      return Jwts.builder()
              .setClaims(claims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationMs))
              .signWith(SignatureAlgorithm.HS256, secret)
              .compact();
  }


  public boolean validateToken(String token){
    try{
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

      return true;
    }catch(JwtException | IllegalArgumentException ex){
      return false;
    }
  }

  public String extractUsername(String token){
    return extractClaim(token,Claims::getSubject);
  }

  public List<String> extractRoles(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return (List<String>) claims.get("roles");
    }

  private <T> T extractClaim(String token,Function<Claims,T>resolver){
    Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return resolver.apply(claims);
  }
}
