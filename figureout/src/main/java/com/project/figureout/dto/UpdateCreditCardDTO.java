package com.project.figureout.dto;

import com.project.figureout.validation.OnlyOnePreferentialCreditCard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@OnlyOnePreferentialCreditCard
@Getter @Setter
public class UpdateCreditCardDTO {

    @NotNull
    private long clientId;

    @NotNull
    private long creditCardId;

    @NotNull(message = "O campo 'preferido' não pode ser nulo.")
    private boolean preferential;

    @NotBlank(message = "O número do cartão não pode estar vazio.")
    //@CreditCardNumber(message = "Insira um número de cartão de crédito válido.")
    //@Pattern(message = "Não insira letras, caracteres especiais, ou espaços.", regexp = "^[\\d]+$")
    private String cardNumber;

    @NotBlank(message = "O nome impresso não pode estar vazio.")
    @Pattern(message = "O nome impresso não pode possuir números.", regexp = "^[A-Za-z\\s]+$")
    private String printedName;

    @NotNull(message = "A bandeira do cartão não pode ser nula.")
    private CreditCardBrandDTO creditCardBrandDTO;

    @NotBlank(message = "O código de segurança do cartão não pode estar em branco.")
    @Pattern(message = "O código de segurança não pode ter letras, caracteres especiais, ou espaços, e pode ter no máximo 4 caracteres.", regexp = "[\\d]{3,4}")
    private String securityCode;

}
