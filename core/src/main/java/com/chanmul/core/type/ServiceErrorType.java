package com.chanmul.core.type;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceErrorType {
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "001-0000", "잘못된 요청입니다"),
  CREATED(HttpStatus.CREATED, "001-0001", "등록 되었습니다."),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "001-0002", "비 인가 사용자입니다."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "001-0003", "권한이 없습니다."),
  NOT_FOUND(HttpStatus.NOT_FOUND, "001-0004", "해당 리소스를 찾을 수 없습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "001-0005", "시스템에 문제가 발생하였습니다."),
  INVALID_PARAMETER(HttpStatus.CONFLICT, "001-0006", "유효하지 않은 전달값입니다."),
  LOGOUT_USER(HttpStatus.BAD_REQUEST, "001-0007", "로그아웃 된 사용자입니다."),
  INVALID_USER_TOKEN(HttpStatus.BAD_REQUEST, "001-0008", "토큰의 정보가 일치하지 않습니다."),
  INVALID_USER_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "001-0009", "리프레시 토큰이 유효하지 않습니다."),

  EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "001-0010", "만료된 토큰입니다."),
  WAS_LOGOUT_USER(HttpStatus.BAD_REQUEST, "001-0011", "로그아웃 된 사용자입니다."),

  INVALID_USER_NAME(HttpStatus.BAD_REQUEST, "001-0012", "이름은 한글, 영문 대소문자만 허용합니다.."),
  INVALID_USER_NICKNAME(HttpStatus.BAD_REQUEST, "001-0013", "별명은 영문 소문자만 허용합니다."),
  INVALID_USER_PASSWORD(HttpStatus.BAD_REQUEST, "001-0014", "비밀번호는 영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상씩 포함합니다."),
  INVALID_USER_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "001-0015", "휴대폰 번호는 숫자만 허용합니다."),

  ;





  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  public static ServiceErrorType from(String code) {
    ServiceErrorType[] enumConstants = ServiceErrorType.class.getEnumConstants();

    for (ServiceErrorType serviceErrorType : enumConstants) {
      if (serviceErrorType.getCode().equals(code.toUpperCase())) {
        return serviceErrorType;
      }
    }

    return INTERNAL_SERVER_ERROR;
  }

  public static String makeCode(Integer status) {
    return String.format("999-%04d", status);
  }
}
