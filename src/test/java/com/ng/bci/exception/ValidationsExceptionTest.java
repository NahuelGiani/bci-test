package com.ng.bci.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ng.bci.context.PreloadContextTest;
import org.junit.jupiter.api.Test;

class ValidationsExceptionTest extends PreloadContextTest {

  @Test
  void testValidationsExceptionWithMessageAndCause() {
    String message = "Some validations failed";
    Throwable cause = new RuntimeException("Root cause");

    ValidationsException exception = new ValidationsException(message, cause);

    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  void testValidationsExceptionWithMessage() {
    String message = "Some validations failed";

    ValidationsException exception = new ValidationsException(message);

    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }

}
