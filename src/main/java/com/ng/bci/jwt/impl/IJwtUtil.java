package com.ng.bci.jwt.impl;

import io.jsonwebtoken.Claims;
import java.io.Serializable;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtUtil extends Serializable {

  public String generateTokenWithUsername(String username);

  public Claims getClaimsFromToken(String token);

  public Boolean validateJwtToken(String token, UserDetails userDetails);

}
