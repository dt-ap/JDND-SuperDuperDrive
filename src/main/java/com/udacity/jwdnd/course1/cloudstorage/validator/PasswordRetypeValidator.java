package com.udacity.jwdnd.course1.cloudstorage.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordRetypeValidator implements ConstraintValidator<PasswordRetype, Object> {
  private String fieldName;
  private String retypeFieldName;

  @Override
  public void initialize(PasswordRetype constraintAnnotation) {
    this.fieldName = constraintAnnotation.fieldName();
    this.retypeFieldName = constraintAnnotation.retypeFieldName();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(fieldName);
    Object retypeFieldValue = new BeanWrapperImpl(value).getPropertyValue(retypeFieldName);

    if (fieldValue != null && retypeFieldValue != null) {
      return fieldValue.equals(retypeFieldValue);
    }

    return false;
  }

}
