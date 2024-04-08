package com.ibm.mateusmelo.validation.constraint;

import com.ibm.mateusmelo.validation.NotEmptyList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, Set> {

    @Override
    public boolean isValid(Set list, ConstraintValidatorContext context) {
        return list != null && !list.isEmpty();
    }

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
