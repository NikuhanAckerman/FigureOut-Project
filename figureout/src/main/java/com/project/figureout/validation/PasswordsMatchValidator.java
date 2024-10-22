package com.project.figureout.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, Object> {

    private String passwordFieldName;
    private String confirmPasswordFieldName;

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        this.passwordFieldName = "password";
        this.confirmPasswordFieldName = "confirmPassword";
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {

        try {
            Field passwordField = obj.getClass().getDeclaredField(passwordFieldName);
            Field confirmPasswordField = obj.getClass().getDeclaredField(confirmPasswordFieldName);

            passwordField.setAccessible(true);
            confirmPasswordField.setAccessible(true);

            String passwordValue = (String) passwordField.get(obj);
            String confirmPasswordValue = (String) confirmPasswordField.get(obj);

            return (passwordValue != null && passwordValue.equals(confirmPasswordValue));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
