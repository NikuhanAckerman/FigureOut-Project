package com.project.figureout.dto;

import com.project.figureout.model.Gender;
import com.project.figureout.model.Phone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class ClientBasicDataDTO {

    @NotBlank(message = "O nome não pode estar vazio.")
    private String name;

    @NotBlank(message = "O e-mail não pode estar vazio.")
    private String email;

    @NotBlank(message = "O CPF não pode estar vazio.")
    @Size(max = 14)
    private String cpf;

    @NotBlank(message = "A senha não pode estar vazia.")
    private String password;

    @NotBlank(message = "A confirmação de senha não pode estar vazia.")
    private String confirmPassword;

    @NotBlank(message = "A data de nascimento não pode estar vazia.")
    @Past(message = "A data de nascimento deve estar no passado.")
    private LocalDate birthday;

    @NotBlank(message = "O campo de 'ativo' não pode estar vazio.")
    private boolean enabled = true;

    //@NotBlank(message = "O gênero não pode estar vazio.")
    private Gender gender;

    //@NotBlank(message = "O telefone não pode estar vazio.")
    private Phone phone;

}
