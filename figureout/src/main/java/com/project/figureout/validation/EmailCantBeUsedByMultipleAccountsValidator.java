package com.project.figureout.validation;

import com.project.figureout.model.Client;
import com.project.figureout.service.ClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmailCantBeUsedByMultipleAccountsValidator implements ConstraintValidator<EmailCantBeUsedByMultipleAccounts, String> {

    @Autowired
    ClientService clientService;

    @Override
    public void initialize(EmailCantBeUsedByMultipleAccounts constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        List<Client> allClients = clientService.getAllClients();

        for (Client client : allClients) {
            if (client.getEmail().equals(s)) {
                return false;
            }
        }

        return true;
    }
}
