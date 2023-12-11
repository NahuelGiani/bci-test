package com.ng.bci.jwt.impl;

import com.ng.bci.repository.IUserRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  private IUserRepository userRepository;

  public JwtUserDetailsService(final IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<com.ng.bci.domain.User> user = userRepository.findByName(username);
    if (user.isPresent()) {
      return new User(user.get().getName(), user.get().getPassword(), new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
}
