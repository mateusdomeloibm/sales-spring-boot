package com.ibm.mateusmelo.validation;

import com.ibm.mateusmelo.validation.constraint.NotEmptyListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotEmptyListValidator.class})
@Target(value = ElementType.FIELD)
public @interface NotEmptyList {
    String message() default "List can not be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
