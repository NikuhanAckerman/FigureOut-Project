package com.project.figureout.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CreditCardDTO {

    @NotEmpty(message = "Este cartão é o preferencial?")
    private boolean preferido;

    @NotEmpty(message = "Insira o número do cartão")
    private String cardNumber;

    @NotEmpty(message = "Insira o nome impresso em seu cartão.")
    private String printedName;

    @NotEmpty(message = "Insira a bandeira do cartão.")
    private String brand;

    @NotEmpty(message = "Insira o código de segurança do cartão")
    private String securityCode;

    //@NotEmpty(message = "Insira a data de validade do cartão")
    //@Getter @Setter private Date expiration;



}
