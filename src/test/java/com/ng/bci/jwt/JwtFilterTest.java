package com.ng.bci.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ng.bci.context.PreloadJwtContextTest;
import com.ng.bci.jwt.impl.IJwtUtil;
import com.ng.bci.jwt.impl.JwtFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

class JwtFilterTest extends PreloadJwtContextTest {

  @MockBean
  private IJwtUtil jwtUtil;

  @MockBean
  private HttpServletRequest request;

  @MockBean
  private HttpServletResponse response;

  @MockBean
  private FilterChain filterChain;

  @MockBean
  private UserDetailsService userDetailsService;

  private TestableJwtFilter testableJwtFilter;

  @BeforeEach
  void beforeEach() {
    filterChain = mock(FilterChain.class);
    testableJwtFilter = new TestableJwtFilter(jwtUtil, userDetailsService);
    SecurityContextHolder.clearContext();
  }

  @Test
  void doFilterInternalValidToken() throws ServletException, IOException {
    String token = "123456789";

    when(request.getHeader("bci-token"))
        .thenReturn("Bearer " + token);

    Claims claims = mock(Claims.class);

    when(claims.getSubject())
        .thenReturn("Nahu");

    when(jwtUtil.getClaimsFromToken(token))
        .thenReturn(claims);

    UserDetails userDetails = new User("Nahu", "password1234", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

    when(userDetailsService.loadUserByUsername("Nahu"))
        .thenReturn(userDetails);

    when(jwtUtil.validateJwtToken(token, userDetails))
        .thenReturn(true);

    testableJwtFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain, times(1)).doFilter(request, response);
    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    assertEquals("Nahu", SecurityContextHolder.getContext().getAuthentication().getName());
  }

  @Test
  void doFilterInternalWithInvalidToken() throws ServletException, IOException {
    when(request.getHeader("bci-token"))
      .thenReturn("Bearer invalidToken");
   
    when(jwtUtil.getClaimsFromToken("invalidToken"))
      .thenThrow(IllegalArgumentException.class);

    testableJwtFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain, times(1)).doFilter(request, response);
    
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }

  @Test
  void doFilterInternalWithoutHeaderThrowsJwtException() throws ServletException, IOException {
    when(request.getHeader("bci-token"))
      .thenReturn(null);
   
    assertThrows(JwtException.class, () -> testableJwtFilter.doFilterInternal(request, response, filterChain));
  }

  public class TestableJwtFilter extends JwtFilter {

    public TestableJwtFilter(IJwtUtil jwtUtil, UserDetailsService userDetailsService) {
      super(jwtUtil, userDetailsService);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
      super.doFilterInternal(request, response, filterChain);
    }
  }

}
