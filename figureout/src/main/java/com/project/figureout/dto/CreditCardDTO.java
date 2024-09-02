package com.project.figureout.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class CreditCardDTO {

    @NotEmpty(message = "Insira o nome impresso em seu cartão.")
    @Getter @Setter private String printedName;

    @NotEmpty(message = "Insira o número do cartão")
    @Getter @Setter private String cardNumber;

    @NotEmpty(message = "Insira o código de segurança do cartão")
    @Getter @Setter private String securityCode;

    @NotEmpty(message = "Insira a data de validade do cartão")
    @Getter @Setter private Date expiration;

    @NotEmpty(message = "Insira o país do cartão.")
    @Getter @Setter private String country;

}
