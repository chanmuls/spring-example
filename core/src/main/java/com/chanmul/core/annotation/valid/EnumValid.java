package com.chanmul.core.annotation.valid;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Enum Validator
 *
 * @NotNull 과 함께 사용하면 필수 체크 항목이입니다.
 *
 * @NotNull
 * @EnumValid(enumClass = SampleEnum.class)
 * private String sample;
 *
 */
@Documented
@Constraint(validatedBy = {EnumValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {
  String message() default "Invalid value. This is not permitted.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends Enum<?>> enumClass();

  boolean ignoreCase() default false;
}
