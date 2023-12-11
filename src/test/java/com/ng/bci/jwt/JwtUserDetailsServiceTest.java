package com.ng.bci.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ng.bci.context.PreloadJwtContextTest;
import com.ng.bci.domain.User;
import com.ng.bci.jwt.impl.JwtUserDetailsService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class JwtUserDetailsServiceTest extends PreloadJwtContextTest {

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @Test
  void loadUserByUsernameUserFound() {
    String username = "Nahu22";
    User user = new User(username, "ng@gmail.com", "pass1234", null);

    when(userRepository.findByName(username))
        .thenReturn(Optional.of(user));

    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

    assertNotNull(userDetails);
    assertEquals(username, userDetails.getUsername());
    assertEquals("pass1234", userDetails.getPassword());
    assertTrue(userDetails.getAuthorities().isEmpty());

    verify(userRepository, times(1)).findByName(username);
  }

  @Test
  void loadUserByUsernameButUserNotFound() {

    String username = "NGNotLoaded";

    when(userRepository.findByName(username))
        .thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername(username));

    verify(userRepository, times(1)).findByName(username);
  }

}
