package com.project.figureout.validation;

import com.project.figureout.dto.CreditCardDTO;
import com.project.figureout.model.Client;
import com.project.figureout.model.CreditCard;
import com.project.figureout.service.ClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.List;

public class OnlyOnePreferentialCreditCardValidator implements ConstraintValidator<OnlyOnePreferentialCreditCard, Object> {

    @Autowired
    ClientService clientService;

    @Override
    public void initialize(OnlyOnePreferentialCreditCard constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        if(obj instanceof CreditCardDTO){
            CreditCardDTO creditCardDTO = (CreditCardDTO) obj;

            String clientId = creditCardDTO.getClientId();

            Client client = clientService.getClientById(Long.parseLong(clientId));

            List<CreditCard> clientCreditCards = client.getCreditCards();

            for(CreditCard creditCard: clientCreditCards) {

                if(creditCard.isPreferido()) {
                    return false;
                }

            }

            return true;

        }


        return false;
    }

}
