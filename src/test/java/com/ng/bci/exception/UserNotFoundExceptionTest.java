package com.ng.bci.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ng.bci.context.PreloadContextTest;
import org.junit.jupiter.api.Test;

class UserNotFoundExceptionTest extends PreloadContextTest {

  @Test
  void testUserNotFoundExceptionWithMessageAndCause() {
    String message = "User carlitos not found";
    Throwable cause = new RuntimeException("Root cause");

    UserNotFoundException exception = new UserNotFoundException(message, cause);

    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  void testUserNotFoundExceptionWithMessage() {
    String message = "User carlitos not found";

    UserNotFoundException exception = new UserNotFoundException(message);

    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

}
