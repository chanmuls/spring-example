package com.chanmul.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.chanmul.core.response.ApiErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiErrorResponse> handleApiExceptions(ApiException ex) {
    log.error("message: {}, serviceErrorType: {}", ex.getMessage(), ex.getServiceErrorType(), ex);

    ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
    apiErrorResponse.setError(ex.getServiceErrorType());

    return new ResponseEntity<>(apiErrorResponse, ex.getServiceErrorType().getHttpStatus());
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
    log.error("message: {}", ex.getMessage(), ex);

    String message = ex.getBindingResult().getAllErrors().stream().findFirst()
        .orElse(new ObjectError("validationParam", ex.getMessage()))
        .getDefaultMessage();

    ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
    apiErrorResponse.setError(message);

    return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiErrorResponse> handleBindExceptions(BindException ex) {
    String message = ex.getBindingResult().getAllErrors().stream().findFirst()
        .orElse(new ObjectError("validationParam", ex.getMessage()))
        .getDefaultMessage();

    ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
    apiErrorResponse.setError(message);

    return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiErrorResponse> handleConstraintViolationExceptions(RuntimeException ex) {
    log.error("message: {}", ex.getMessage(), ex);

    ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
    apiErrorResponse.setErrorByUnknown(ex.getMessage());
    return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
  }
}
