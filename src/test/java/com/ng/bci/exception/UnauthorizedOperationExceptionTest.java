package com.ng.bci.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ng.bci.context.PreloadContextTest;
import org.junit.jupiter.api.Test;

class UnauthorizedOperationExceptionTest extends PreloadContextTest {

  @Test
  void testUnauthorizedOperationExceptionWithMessageAndCause() {
    String message = "Unauthorized Operation";
    Throwable cause = new RuntimeException("Root cause");

    UnauthorizedOperationException exception = new UnauthorizedOperationException(message, cause);

    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

  @Test
  void testUnauthorizedOperationExceptionWithMessage() {
    String message = "Unauthorized Operation";

    UnauthorizedOperationException exception = new UnauthorizedOperationException(message);

    assertEquals(message, exception.getMessage());
    assertNull(exception.getCause());
  }
  
}
