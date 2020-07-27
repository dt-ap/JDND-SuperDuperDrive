package com.udacity.jwdnd.course1.cloudstorage.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordRetypeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordRetype {
  String message() default "Passwords do not match.";

  String fieldName() default "password";

  String retypeFieldName() default "retypePassword";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
