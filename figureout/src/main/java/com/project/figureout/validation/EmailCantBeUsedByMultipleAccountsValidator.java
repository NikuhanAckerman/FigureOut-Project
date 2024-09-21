package com.project.figureout.validation;

import com.project.figureout.model.Client;
import com.project.figureout.service.ClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmailCantBeUsedByMultipleAccountsValidator implements ConstraintValidator<EmailCantBeUsedByMultipleAccounts, Object> {

    @Autowired
    ClientService clientService;

    @Override
    public void initialize(EmailCantBeUsedByMultipleAccounts constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {

        if(obj instanceof Client) {
            Client client = (Client) obj;
            List<Client> allClients = clientService.getAllClients();

            for(Client clientObjectInsideList: allClients) {

                if(clientObjectInsideList.getEmail().equals(client.getEmail())) {
                    if(!clientObjectInsideList.equals(client)) {
                        return false;
                    }
                }

            }

            return true;
        }

        return true;

    }
}
