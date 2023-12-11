package com.ng.bci.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ng.bci.context.PreloadContextTest;
import org.junit.jupiter.api.Test;

class EmailAlreadyExistsExceptionTest extends PreloadContextTest {

  @Test
  void testEmailAlreadyExistsExceptionWithMessageAndCause() {
    String message = "The email already exists in database";
    Throwable cause = new RuntimeException("Root cause");

    EmailAlreadyExistsException exception = new EmailAlreadyExistsException(message, cause);

    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  void testEmailAlreadyExistsExceptionWithMessage() {
    String message = "The email already exists in database";

    EmailAlreadyExistsException exception = new EmailAlreadyExistsException(message);

    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

}
