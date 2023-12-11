package com.ng.bci.context;

import com.ng.bci.jwt.impl.IJwtUtil;
import com.ng.bci.repository.IPhoneRepository;
import com.ng.bci.repository.IUserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class PreloadServiceContextTest extends PreloadContextTest {

  @MockBean
  protected IUserRepository userRepository;

  @MockBean
  protected IPhoneRepository phoneRepository;

  @MockBean
  protected IJwtUtil jwtUtil;

  @MockBean
  protected PasswordEncoder passwordEncoder;

  @MockBean
  protected Authentication authentication;

}
