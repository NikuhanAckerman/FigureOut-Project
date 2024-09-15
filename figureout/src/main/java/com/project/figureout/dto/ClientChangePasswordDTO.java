package com.project.figureout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientChangePasswordDTO {

    @NotBlank(message = "A senha não pode estar vazia.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&-_])[A-Za-z\\d@$!%*?&]{8}$")
    private String password;

    @NotBlank(message = "A confirmação de senha não pode estar vazia.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&-_])[A-Za-z\\d@$!%*?&]{8}$")
    private String confirmPassword;

}
