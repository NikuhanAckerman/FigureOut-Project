package com.project.figureout.validation.wrappers;

import com.project.figureout.dto.CreditCardDTO;
import com.project.figureout.model.CreditCard;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreditCardWrapper {

    @Valid
    private CreditCard creditCard;

    @Valid
    private CreditCardDTO creditCardDTO = new CreditCardDTO();

}
