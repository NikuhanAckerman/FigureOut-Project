package com.project.figureout.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ClientDTO {

    @NotEmpty(message = "Digite o nome.")
    private String name;

    @NotEmpty(message = "Digite o email.")
    private String email;

    @NotEmpty(message = "Digite o número de telefone.")
    @Size(min = 15, max = 15)
    // (11) 98224-8652
    private String phoneNumber;

    @Size(min = 14, max = 14)
    @NotEmpty(message = "Digite o CPF.")
    private String cpf;

    @NotEmpty(message = "Digite o endereço.")
    private String address;

}
