package com.project.figureout.dto;

import com.project.figureout.model.Gender;
import com.project.figureout.model.Phone;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class ClientBasicDataDTO {

    @NotBlank(message = "O nome não pode estar vazio.")
    @Pattern(message = "O nome possui caracteres não permitidos.", regexp = "^[A-Za-zÀ-ÖØ-Ýà-öø-ÿ\\s]*$")
    private String name;

    @NotBlank(message = "O e-mail não pode estar vazio.")
    @Email(message = "Este e-mail não é válido.")
    private String email;

    @NotBlank(message = "O CPF não pode estar vazio.")
    @Size(max = 14)
    @Pattern(message = "O formato é inválido. O CPF deve estar no formato: '123.456.789-00'.", regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    private String cpf;

    @NotBlank(message = "A senha não pode estar vazia.")
    @Pattern(message = "A senha digitada é inválida.", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct})[A-Za-z\\d\\p{Punct}]{8,}$")
    private String password;

    @NotBlank(message = "A confirmação de senha não pode estar vazia.")
    @Pattern(message = "A senha digitada é inválida.", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct})[A-Za-z\\d\\p{Punct}]{8,}$")
    private String confirmPassword;

    @NotNull(message = "A data de nascimento não pode ser nula.")
    @Past(message = "A data de nascimento deve estar no passado.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull(message = "O campo 'ativo' não pode ser nulo.")
    private boolean enabled = true;

    @NotNull(message = "O gênero não pode ser nulo.")
    private GenderDTO genderDTO;

    @NotNull(message = "O telefone não pode ser nulo.")
    private PhoneDTO phoneDTO;

}
