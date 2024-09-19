package com.project.figureout.validation;

import com.project.figureout.model.Client;
import com.project.figureout.service.ClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CpfCantBeUsedByMultipleAccountsValidator implements ConstraintValidator<CpfCantBeUsedByMultipleAccounts, String> {

    @Autowired
    ClientService clientService;

    @Override
    public void initialize(CpfCantBeUsedByMultipleAccounts constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        List<Client> allClients = clientService.getAllClients();

        String cpfFieldTreated = clientService.treatMaskedCpf(s);

        for (Client client : allClients) {
            if (client.getCpf().equals(cpfFieldTreated)) {
                return false;
            }
        }

        return true;
    }
}
