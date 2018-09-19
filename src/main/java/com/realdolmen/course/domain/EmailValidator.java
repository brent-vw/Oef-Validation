package com.realdolmen.course.domain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {
    private String domain;

    @Override
    public void initialize(Email constraintAnnotation) {
        this.domain = constraintAnnotation.domain();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.toLowerCase().endsWith("@".concat(domain).toLowerCase());
    }
}
