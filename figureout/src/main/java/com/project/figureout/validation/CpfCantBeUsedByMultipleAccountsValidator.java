package com.project.figureout.validation;

import com.project.figureout.dto.ClientBasicDataDTO;
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

        if(obj instanceof ClientBasicDataDTO) {
            constraintValidatorContext.disableDefaultConstraintViolation();

            constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("cpf").addConstraintViolation();

            ClientBasicDataDTO clientBasicDataDTO = (ClientBasicDataDTO) obj;
            String treatedClientBasicDataDTOCpf = clientService.treatMaskedCpf(clientBasicDataDTO.getCpf());
            List<Client> allClients = clientService.getAllClients();
            long id = clientBasicDataDTO.getClientId();

            if(id == 0) { // creating client
                for(Client currentClient: allClients) {

                    String treatedClientCpf = clientService.treatMaskedCpf(currentClient.getCpf());

                    if(treatedClientCpf.equals(treatedClientBasicDataDTOCpf)) {
                        return false;
                    }

                }
            } else { // updating client
                Client client = clientService.getClientById(id);

                for(Client currentClient: allClients) {

                    String treatedClientCpf = clientService.treatMaskedCpf(currentClient.getCpf());

                    if(treatedClientCpf.equals(treatedClientBasicDataDTOCpf)) {

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
