package com.project.figureout.validation;

import com.project.figureout.dto.ClientBasicDataDTO;
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

        if(obj instanceof ClientBasicDataDTO) {
            ClientBasicDataDTO clientBasicDataDTO = (ClientBasicDataDTO) obj;

            long id = clientBasicDataDTO.getClientId();
            Client client = clientService.getClientById(id);
            List<Client> allClients = clientService.getAllClients();

            for(Client clientObjectInsideList: allClients) {

                if(clientObjectInsideList.getEmail().equals(clientBasicDataDTO.getEmail())) {
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
