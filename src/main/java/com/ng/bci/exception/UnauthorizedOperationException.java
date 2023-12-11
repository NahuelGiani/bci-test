package com.ng.bci.exception;

public class UnauthorizedOperationException extends RuntimeException {

  private static final long serialVersionUID = -8564421673373937109L;

  public UnauthorizedOperationException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnauthorizedOperationException(String message) {
    super(message);
  }

}
