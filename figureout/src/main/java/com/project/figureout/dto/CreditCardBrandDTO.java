package com.project.figureout.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreditCardBrandDTO {

    @NotEmpty(message = "O id da bandeira do cartão de crédito não pode ser nulo.")
    private long id;

}
