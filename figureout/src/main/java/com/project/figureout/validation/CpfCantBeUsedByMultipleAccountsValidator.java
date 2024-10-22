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
            ClientBasicDataDTO clientBasicDataDTO = (ClientBasicDataDTO) obj;
            long id = clientBasicDataDTO.getClientId();
            Client client = clientService.getClientById(id);
            List<Client> allClients = clientService.getAllClients();

            String treatedClientBasicDataDTOCpf = clientService.treatMaskedCpf(clientBasicDataDTO.getCpf());

            for(Client clientObjectInsideList: allClients) {

                String treatedClientCpf = clientService.treatMaskedCpf(clientObjectInsideList.getCpf());

                if(treatedClientCpf.equals(treatedClientBasicDataDTOCpf)) {
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
