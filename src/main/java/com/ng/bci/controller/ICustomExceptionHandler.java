package com.ng.bci.controller;

import com.ng.bci.dto.GenericResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ICustomExceptionHandler {

  public ResponseEntity<GenericResponseDTO> handle(final RuntimeException exception);
  
}
