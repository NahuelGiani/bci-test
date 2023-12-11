package com.ng.bci.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ng.bci.context.PreloadContextTest;
import org.junit.jupiter.api.Test;

class PasswordNotValidExceptionTest extends PreloadContextTest {

  @Test
  void testPasswordNotValidExceptionWithMessageAndCause() {
    String message = "The password does not match the required pattern: xxxxx";
    Throwable cause = new RuntimeException("Root cause");

    PasswordNotValidException exception = new PasswordNotValidException(message, cause);

    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  void testPasswordNotValidExceptionWithMessage() {
    String message = "The password does not match the required pattern: xxxxx";

    PasswordNotValidException exception = new PasswordNotValidException(message);

    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

}
