package com.ng.bci.configuration;

import com.ng.bci.jwt.impl.IJwtUtil;
import com.ng.bci.jwt.impl.JwtFilter;
import com.ng.bci.jwt.impl.JwtUserDetailsService;
import com.ng.bci.repository.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecuritySpringConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http, final IUserRepository userRepository, final IJwtUtil jwtUtil,
      final UserDetailsService userDetailsService) throws Exception {
    http.authorizeRequests()
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().httpBasic()
        .and().csrf().disable()
        .cors().disable();
    http.addFilterBefore(new JwtFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web.ignoring().requestMatchers(HttpMethod.POST, "/users")
        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**");
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationProvider authenticationProvider(final IUserRepository userRepository) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(new JwtUserDetailsService(userRepository));
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

}
