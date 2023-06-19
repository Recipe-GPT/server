package com.recipe.gpt.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {

    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues == null) {
            return false;
        }

        boolean result = false;
        for (Object enumValue : enumValues) {
            if (value == enumValue) {
                result = true;
                break;
            }
        }
        return result;
    }

}