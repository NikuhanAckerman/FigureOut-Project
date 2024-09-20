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
        return false;
        /*
        if(obj instanceof CreditCardDTO){
            CreditCardDTO creditCardDTO = (CreditCardDTO) obj;


            //System.out.println("Annotation Method Id output: " + clientId);

            if(creditCardDTO.isPreferido()) {
                List<CreditCard> clientCreditCards = client.getCreditCards();

                if(!clientCreditCards.isEmpty()) {

                    for(CreditCard creditCard: clientCreditCards) {

                        if(creditCard.isPreferido()) {
                            return false;
                        }

                    }
                    return true;
                }

            }

            return true;
        }

        return false;*/
    }

}
