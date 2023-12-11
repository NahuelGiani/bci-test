package com.ng.bci.jwt.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

  private IJwtUtil jwtUtil;
  private UserDetailsService userDetailsService;

  public JwtFilter(final IJwtUtil jwtUtil, final UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String tokenHeader = request.getHeader("bci-token");
    Claims claims = null;
    String token = null;

    if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
      try {
        token = tokenHeader.substring(7);
        claims = jwtUtil.getClaimsFromToken(token);
      } catch (IllegalArgumentException e) {
        LOGGER.error("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        LOGGER.error("JWT Token has expired");
      }
    } else {
      LOGGER.error("BCI-Token not found");
      throw new JwtException("You are not authorized to access the site. (bci-token not found)");
    }
    if (claims != null && claims.getSubject() != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
      if (jwtUtil.validateJwtToken(token, userDetails)) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
            userDetails.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
