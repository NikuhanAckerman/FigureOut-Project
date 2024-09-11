package com.project.figureout.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PhoneDTO {

    @NotEmpty(message = "Insira o tipo de telefone")
    private boolean cellphone;

    @NotEmpty(message = "Insira o seu DDD.")
    private String ddd;

    @NotEmpty(message = "Digite o número de telefone.")
    //@Size(min = 8, max = 10)
    // 98224-8652
    private String phoneNumber;

}
