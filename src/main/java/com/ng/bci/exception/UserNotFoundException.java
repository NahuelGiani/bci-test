package com.ng.bci.exception;

public class UserNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -8564421673373937109L;

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserNotFoundException(String message) {
    super(message);
  }

}
