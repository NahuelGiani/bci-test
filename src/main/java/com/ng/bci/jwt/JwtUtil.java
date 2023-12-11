package com.ng.bci.jwt;

import com.ng.bci.jwt.impl.IJwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil implements IJwtUtil {

  private static final long serialVersionUID = 8117772811918807588L;
  private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

  @Value("${secret.value.jwt}")
  private String secretValue;

  public String generateTokenWithUsername(String username) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList("ROLE_USER");

    return "Bearer " + Jwts.builder()
        .claim("authorities",
            grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList())
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(SignatureAlgorithm.HS512, secretValue)
        .compact();
  }

  public Claims getClaimsFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(secretValue)
        .parseClaimsJws(token)
        .getBody();
  }

  public Boolean validateJwtToken(String token, UserDetails userDetails) {
    String username = getClaimsFromToken(token).getSubject();
    Claims claims = Jwts.parser().setSigningKey(secretValue).parseClaimsJws(token).getBody();
    Boolean isTokenExpired = claims.getExpiration().before(new Date());
    return username.equals(userDetails.getUsername()) && !isTokenExpired;
  }

}
