package com.project.figureout.dto;

import com.project.figureout.validation.PasswordsMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@PasswordsMatch
public class ClientChangePasswordDTO {

    @NotBlank(message = "A senha não pode estar vazia.")
    @Pattern(message = "A senha digitada é inválida.", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct})[A-Za-z\\d\\p{Punct}]{8,}$")
    private String password;

    @NotBlank(message = "A confirmação de senha não pode estar vazia.")
    @Pattern(message = "A senha digitada é inválida.", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct})[A-Za-z\\d\\p{Punct}]{8,}$")
    private String confirmPassword;

}
