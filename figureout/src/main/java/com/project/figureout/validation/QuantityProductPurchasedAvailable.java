package com.project.figureout.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = QuantityProductPurchasedAvailableValidator.class)
public @interface QuantityProductPurchasedAvailable {
    String message() default "A quantidade pedida está indisponível.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
