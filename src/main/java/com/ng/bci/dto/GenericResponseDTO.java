package com.ng.bci.dto;

import org.springframework.http.HttpStatus;

public class GenericResponseDTO {

  private int statusCode;
  private HttpStatus status;
  private String message;

  public GenericResponseDTO(final HttpStatus status, final String message) {
    this.statusCode = status.value();
    this.status = status;
    this.message = message;
  }

  public void setStatus(final int statusCode) {
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatus(final HttpStatus status) {
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
