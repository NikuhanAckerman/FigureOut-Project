package com.project.figureout.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientChangePasswordDTO {

    @NotEmpty(message = "Digite a senha.")
    private String password;

    @NotEmpty(message = "Confirme a senha.")
    private String confirmPassword;

}
