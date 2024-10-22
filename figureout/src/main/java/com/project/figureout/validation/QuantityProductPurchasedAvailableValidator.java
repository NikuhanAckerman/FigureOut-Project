package com.project.figureout.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuantityProductPurchasedAvailableValidator implements ConstraintValidator<QuantityProductPurchasedAvailable, Object> {


    @Override
    public void initialize(QuantityProductPurchasedAvailable constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
