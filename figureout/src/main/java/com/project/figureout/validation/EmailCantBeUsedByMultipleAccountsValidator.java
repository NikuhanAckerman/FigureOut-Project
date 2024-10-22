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
            constraintValidatorContext.disableDefaultConstraintViolation();

            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("email").addConstraintViolation();


            ClientBasicDataDTO clientBasicDataDTO = (ClientBasicDataDTO) obj;
            String clientBasicDataDTOEmail = clientBasicDataDTO.getEmail();
            List<Client> allClients = clientService.getAllClients();
            long id = clientBasicDataDTO.getClientId();

            if(id == 0) { // client creation or no client
                for(Client currentClient: allClients) {

                    String email = currentClient.getEmail();

                    if(email.equals(clientBasicDataDTOEmail)) {
                        return false;
                    }

                }
            } else { // client updating
                Client client = clientService.getClientById(id);

                for(Client currentClient: allClients) {

                    String email = currentClient.getEmail();

                    if(email.equals(clientBasicDataDTOEmail)) {

                        if(!currentClient.equals(client)) {
                            return false;
                        }

                    }

                }
            }

            return true;
        }

        return true;

    }
}
