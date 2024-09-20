package com.project.figureout.dto;

import com.project.figureout.validation.OnlyOnePreferentialCreditCard;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@OnlyOnePreferentialCreditCard
public class CreditCardDTO {

    @NotNull(message = "O campo 'preferido' não pode ser nulo.")
    private boolean preferido;

    @NotBlank(message = "O número do cartão não pode estar vazio.")
    @Pattern(message = "Não insira letras, caracteres especiais, ou espaços.", regexp = "^[\\d]+$")
    private String cardNumber;

    @NotBlank(message = "O nome impresso não pode estar vazio.")
    @Pattern(message = "O nome impresso não pode possuir números.", regexp = "^[A-Za-z\\s]+$")
    private String printedName;

    @NotEmpty(message = "A bandeira do cartão não pode estar vazia.")
    @Pattern(message = "A bandeira não pode possuir números ou caracteres especiais.", regexp = "^[A-Za-z]+[\\s]*$")
    private String brand;

    @NotEmpty(message = "O código de segurança do cartão não pode estar vazio.")
    @Pattern(message = "O código de segurança não pode ter letras, caracteres especiais, ou espaços, e pode ter no máximo 4 caracteres.", regexp = "[\\d]{3,4}")
    private String securityCode;

}
