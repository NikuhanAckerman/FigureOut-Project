package com.project.figureout.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class ClientDTO {

    @NotEmpty(message = "Digite o nome.")
    @Getter @Setter private String name;

    @NotEmpty(message = "Digite o email.")
    @Getter @Setter private String email;

    @NotEmpty(message = "Digite uma senha.")
    @Getter @Setter private String password;

//    @NotEmpty(message = "Digite o número de telefone.")
//    @Size(min = 15, max = 15)
//    // (11) 98224-8652
//    @Getter @Setter private String phoneNumber;

    @Size(min = 14, max = 14)
    @NotEmpty(message = "Digite o CPF.")
    @Getter @Setter private String cpf;

    @NotEmpty(message = "Inativado ou não?")
    @Getter @Setter private boolean disabled = false;

}
