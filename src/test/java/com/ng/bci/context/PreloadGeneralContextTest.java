package com.ng.bci.context;

import com.ng.bci.exception.UnauthorizedOperationException;
import com.ng.bci.exception.UserNotFoundException;
import com.ng.bci.exception.ValidationsException;
import com.ng.bci.service.IUserService;
import io.jsonwebtoken.JwtException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public abstract class PreloadGeneralContextTest extends PreloadContextTest {

  @MockBean
  protected MethodArgumentNotValidException methodArgumentNotValidException;

  @MockBean
  protected ValidationsException validationsException;

  @MockBean
  protected UserNotFoundException userNotFoundException;

  @MockBean
  protected UnauthorizedOperationException unauthorizedOperationException;

  @MockBean
  protected MethodArgumentTypeMismatchException argumentTypeMismatchException;

  @MockBean
  protected JwtException jwtException;

  @MockBean
  protected NullPointerException nullPointerException;

  @MockBean
  protected IUserService userService;

}
