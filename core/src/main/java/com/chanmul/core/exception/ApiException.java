package com.chanmul.core.exception;

import org.springframework.validation.BindingResult;

import com.chanmul.core.type.ServiceErrorType;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ApiException extends IllegalArgumentException {
  private final ServiceErrorType serviceErrorType;
  private BindingResult bindingResult;

  public ApiException(ServiceErrorType serviceErrorType) {
    super(serviceErrorType.getMessage());
    this.serviceErrorType = serviceErrorType;
  }

  public ApiException(ServiceErrorType serviceErrorType, BindingResult bindingResult) {
    super(serviceErrorType.getMessage());
    this.serviceErrorType = serviceErrorType;
    this.bindingResult = bindingResult;
  }
}
