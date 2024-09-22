package com.project.figureout.validation;

import com.project.figureout.dto.CreditCardDTO;
import com.project.figureout.model.Client;
import com.project.figureout.model.CreditCard;
import com.project.figureout.service.ClientService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

            long id = creditCardDTO.getClientId();

            Client client = clientService.getClientById(id);

            List<CreditCard> clientCreditCardList = client.getCreditCards();

            long preferentialCount = clientCreditCardList.stream()
                    .filter(CreditCard::isPreferido)
                    .count();

            return (preferentialCount <= 1);
        }

        return false;
    }

}
