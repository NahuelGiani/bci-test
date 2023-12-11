package com.ng.bci.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ng.bci.context.PreloadJwtContextTest;
import com.ng.bci.jwt.impl.IJwtUtil;
import io.jsonwebtoken.Claims;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

class JwtUtilTest extends PreloadJwtContextTest {

  @Autowired
  private IJwtUtil jwtUtil;

  @Test
  void generateTokenWithUsername() {
    String username = "Nahu";

    String token = jwtUtil.generateTokenWithUsername(username);

    assertNotNull(token);
    assertTrue(token.startsWith("Bearer "));
  }

  @Test
  void getClaimsFromToken() {
    String token = jwtUtil.generateTokenWithUsername("Nahu");

    Claims claims = jwtUtil.getClaimsFromToken(token.substring(7));

    assertNotNull(claims);
    assertEquals("Nahu", claims.getSubject());
  }

  @Test
  void validateJwtTokenValidToken() {
    String token = jwtUtil.generateTokenWithUsername("Nahu");
    User userDetails = new User("Nahu", "password12345", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    boolean isValid = jwtUtil.validateJwtToken(token.substring(7), userDetails);

    assertTrue(isValid);
  }

}
