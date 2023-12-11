package com.ng.bci.context;

import com.ng.bci.repository.IUserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class PreloadJwtContextTest extends PreloadContextTest {

  @MockBean
  protected IUserRepository userRepository;

}
