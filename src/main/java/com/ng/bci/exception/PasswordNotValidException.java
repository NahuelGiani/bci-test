package com.ng.bci.exception;

public class PasswordNotValidException extends ValidationsException {

  private static final long serialVersionUID = -8564421673373937109L;

  public PasswordNotValidException(String message, Throwable cause) {
    super(message, cause);
  }

  public PasswordNotValidException(String message) {
    super(message);
  }

}
