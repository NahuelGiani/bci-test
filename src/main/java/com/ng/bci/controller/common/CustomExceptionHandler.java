package com.ng.bci.controller.common;

import com.google.gson.Gson;
import com.ng.bci.controller.ICustomExceptionHandler;
import com.ng.bci.dto.GenericResponseDTO;
import com.ng.bci.exception.UnauthorizedOperationException;
import com.ng.bci.exception.UserNotFoundException;
import com.ng.bci.exception.ValidationsException;
import io.jsonwebtoken.JwtException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CustomExceptionHandler implements ICustomExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);
  private static final Gson GSON = new Gson();

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<GenericResponseDTO> handleValidationExceptions(final MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    final GenericResponseDTO apiError = new GenericResponseDTO(HttpStatus.BAD_REQUEST, GSON.toJson(errors));
    return createResponseEntity(ex, apiError);
  }

  @ExceptionHandler(ValidationsException.class)
  public ResponseEntity<GenericResponseDTO> handle(final ValidationsException exception) {
    final GenericResponseDTO apiError = new GenericResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage());
    return createResponseEntity(exception, apiError);
  }
  
  @ExceptionHandler(JwtException.class)
  public ResponseEntity<GenericResponseDTO> handle(final JwtException exception) {
    final GenericResponseDTO apiError = new GenericResponseDTO(HttpStatus.UNAUTHORIZED, exception.getMessage());
    return createResponseEntity(exception, apiError);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<GenericResponseDTO> handle(final UserNotFoundException exception) {
    final GenericResponseDTO apiError = new GenericResponseDTO(HttpStatus.NOT_FOUND, exception.getMessage());
    return createResponseEntity(exception, apiError);
  }
  
  @ExceptionHandler(UnauthorizedOperationException.class)
  public ResponseEntity<GenericResponseDTO> handle(final UnauthorizedOperationException exception) {
    final GenericResponseDTO apiError = new GenericResponseDTO(HttpStatus.UNAUTHORIZED, exception.getMessage());
    return createResponseEntity(exception, apiError);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<GenericResponseDTO> handle(final MethodArgumentTypeMismatchException exception) {
    final GenericResponseDTO apiError = new GenericResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage());
    return createResponseEntity(exception, apiError);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<GenericResponseDTO> handle(final RuntimeException exception) {
    final GenericResponseDTO apiError = new GenericResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    return createResponseEntity(exception, apiError);
  }

  private ResponseEntity<GenericResponseDTO> createResponseEntity(final Throwable exception, final GenericResponseDTO apiError) {
    LOGGER.error(exception.getMessage(), exception);
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
