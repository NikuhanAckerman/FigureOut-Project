package com.project.figureout.validation;

import com.project.figureout.model.Client;
import com.project.figureout.service.ClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CpfCantBeUsedByMultipleAccountsValidator implements ConstraintValidator<CpfCantBeUsedByMultipleAccounts, Object> {

    @Autowired
    ClientService clientService;

    @Override
    public void initialize(CpfCantBeUsedByMultipleAccounts constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {

        if(obj instanceof Client) {
            Client client = (Client) obj;
            List<Client> allClients = clientService.getAllClients();

            String cpfFieldTreated = clientService.treatMaskedCpf(client.getCpf());

            for(Client clientObjectInsideList: allClients) {

                if(clientObjectInsideList.getCpf().equals(cpfFieldTreated)) {
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
