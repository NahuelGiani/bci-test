package com.ng.bci.exception;

public class ValidationsException extends RuntimeException {

  private static final long serialVersionUID = 1974533375924860656L;

  public ValidationsException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidationsException(String message) {
    super(message);
  }

}
