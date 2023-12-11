package com.ng.bci.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ng.bci.context.PreloadGeneralContextTest;
import com.ng.bci.controller.common.CustomExceptionHandler;
import com.ng.bci.dto.GenericResponseDTO;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

class CustomExceptionHandlerTest extends PreloadGeneralContextTest {

  @Autowired
  private CustomExceptionHandler customExceptionHandler;

  @Test
  void handleMethodArgumentNotValidException() {
    when(methodArgumentNotValidException.getBindingResult())
      .thenReturn(mock(BindingResult.class));
    when(methodArgumentNotValidException.getBindingResult().getAllErrors())
      .thenReturn(Collections.singletonList(new FieldError("password", "password", "The password cannot be null")));

    ResponseEntity<GenericResponseDTO> responseEntity = customExceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void handleValidationsException() {
    when(validationsException.getMessage()).thenReturn("Validation error");

    ResponseEntity<GenericResponseDTO> responseEntity = customExceptionHandler.handle(validationsException);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void handleUserNotFoundException() {
    when(userNotFoundException.getMessage()).thenReturn("User not found");

    ResponseEntity<GenericResponseDTO> responseEntity = customExceptionHandler.handle(userNotFoundException);

    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
  }

  @Test
  void handleUnauthorizedOperationException() {
    when(unauthorizedOperationException.getMessage()).thenReturn("Unauthorized operation");

    ResponseEntity<GenericResponseDTO> responseEntity = customExceptionHandler.handle(unauthorizedOperationException);

    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }

  @Test
  void handleMethodArgumentTypeMismatchException() {
    when(argumentTypeMismatchException.getMessage()).thenReturn("Method argument type mismatch");

    ResponseEntity<GenericResponseDTO> responseEntity = customExceptionHandler.handle(argumentTypeMismatchException);

    assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
  }

  @Test
  void handleJwtException() {
    when(jwtException.getMessage()).thenReturn("You are not authorized to access the site. (bci-token not found)");

    ResponseEntity<GenericResponseDTO> responseEntity = customExceptionHandler.handle(jwtException);

    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
  }

  @Test
  void handleRuntimeException() {
    ResponseEntity<GenericResponseDTO> responseEntity = customExceptionHandler.handle(nullPointerException);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
  }

}
