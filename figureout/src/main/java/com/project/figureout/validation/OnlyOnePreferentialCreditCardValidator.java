package com.project.figureout.validation;

import com.project.figureout.dto.CreditCardDTO;
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
        if(obj instanceof CreditCard){
            CreditCard creditCard = (CreditCard) obj;

            Client client = creditCard.getClient();

            System.out.println("CreditCardValidator: " + client.getId());

            List<CreditCard> clientCreditCardList = client.getCreditCards();

            for(CreditCard currentCreditCard : clientCreditCardList) {

                if(currentCreditCard.getId() == creditCard.getId()){
                    return true;
                }

            }

            long preferentialCount = clientCreditCardList.stream()
                    .filter(CreditCard::isPreferential)
                    .count();

            return (preferentialCount <= 1);
        }

        return false;
    }

}
