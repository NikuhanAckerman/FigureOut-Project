package com.project.figureout.validation;

import com.project.figureout.dto.CreditCardDTO;
import com.project.figureout.dto.UpdateCreditCardDTO;
import com.project.figureout.model.Client;
import com.project.figureout.model.CreditCard;
import com.project.figureout.service.ClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Component
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

            if(creditCardDTO.isPreferential()) {
                long clientId = creditCardDTO.getClientId();

                Client client = clientService.getClientById(clientId);

                List<CreditCard> clientCreditCardList = client.getCreditCards();

                long preferentialCount = clientCreditCardList.stream()
                        .filter(CreditCard::isPreferential)
                        .count();

                return (preferentialCount < 1);
            }

            return true;
        } else if(obj instanceof UpdateCreditCardDTO) {
            UpdateCreditCardDTO updateCreditCardDTO = (UpdateCreditCardDTO) obj;
            long clientId = updateCreditCardDTO.getClientId();
            long creditCardId = updateCreditCardDTO.getCreditCardId();

            if(updateCreditCardDTO.isPreferential()) {
                Client client = clientService.getClientById(clientId);

                List<CreditCard> clientCreditCardList = client.getCreditCards();

                CreditCard creditCardBeingUpdated = null;

                long preferentialCount = clientCreditCardList.stream()
                        .filter(CreditCard::isPreferential)
                        .count();

                for(CreditCard creditCard : clientCreditCardList){

                    if(creditCard.getId() == creditCardId){

                        creditCardBeingUpdated = creditCard;
                        break;

                    }

                }

                if(!creditCardBeingUpdated.isPreferential()) {

                    if(preferentialCount >= 1) {
                        return false;
                    }

                }

                return (preferentialCount <= 1);
            }

            return true;
        }

        return false;
    }

}
