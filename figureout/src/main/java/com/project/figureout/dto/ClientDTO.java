package com.project.figureout.dto;

import com.project.figureout.model.Gender;
import com.project.figureout.model.Phone;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ClientDTO {

    @NotEmpty(message = "Digite o nome.")
    @Getter @Setter private String name;

    @NotEmpty(message = "Digite o email.")
    @Getter @Setter private String email;

    @Size(min = 14, max = 14)
    @NotEmpty(message = "Digite o CPF.")
    @Getter @Setter private String cpf;

    @NotEmpty(message = "Digite uma senha.")
    @Getter @Setter private String password;

    @NotEmpty(message = "Digite a data de nascimento.")
    @Getter @Setter private Date birthday;

    @NotEmpty(message = "Ativo ou não?")
    @Getter @Setter private boolean enabled = true;

}