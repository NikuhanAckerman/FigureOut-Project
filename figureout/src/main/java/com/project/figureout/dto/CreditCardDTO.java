package com.project.figureout.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CreditCardDTO {

    @NotNull(message = "O campo 'preferido' não pode estar vazio.")
    private boolean preferido;

    @NotBlank(message = "O número do cartão não pode estar vazio.")
    @Pattern(regexp = "^[\\d]$")
    private String cardNumber;

    @NotBlank(message = "O nome impresso não pode estar vazio.")
    @Pattern(regexp = "^[A-Za-z\\s]+$")
    private String printedName;

    @NotEmpty(message = "A bandeira do cartão não pode estar vazia.")
    @Pattern(regexp = "^[A-Za-z\\s]*$")
    private String brand;

    @NotEmpty(message = "O código de segurança do cartão não pode estar vazio.")
    @Pattern(regexp = "[\\d]{3,4}")
    private String securityCode;

}
