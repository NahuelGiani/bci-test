package com.ng.bci.exception;

public class EmailAlreadyExistsException extends ValidationsException {

  private static final long serialVersionUID = 8046562338388540055L;

  public EmailAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  public EmailAlreadyExistsException(String message) {
    super(message);
  }

}
