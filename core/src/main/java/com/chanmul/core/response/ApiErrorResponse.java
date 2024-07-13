package com.chanmul.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.chanmul.core.type.ServiceErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
  private ApiError error;

  public void setError(String message, String code) {
    this.error = new ApiError(message, code);
  }

  public void setError(ServiceErrorType serviceErrorType) {
    this.error = new ApiError(serviceErrorType.getMessage(), serviceErrorType.getCode());
  }

  public void setError(String message) {
    this.error = new ApiError(message, ServiceErrorType.BAD_REQUEST.getCode());
  }

  public void setErrorByUnknown(String message) {
    this.error = new ApiError(message, ServiceErrorType.BAD_REQUEST.getCode(),
        ServiceErrorType.BAD_REQUEST.getMessage());
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  private static class ApiError {
    private String message;
    private String code;
    private String usermessage;

    public ApiError(String message, String code) {
      this.message = message;
      this.usermessage = message;
      this.code = code;
    }
  }
}
