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

    //@NotBlank(message = "O e-mail não pode estar vazio.")
    //@Email
    private String email;

    //@NotBlank(message = "O CPF não pode estar vazio.")
    //@Size(max = 14)
    //@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")
    private String cpf;

    //@NotBlank(message = "A senha não pode estar vazia.")
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&-_])[A-Za-z\\d@$!%*?&]{8}$")
    private String password;

    //@NotBlank(message = "A confirmação de senha não pode estar vazia.")
    //@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&-_])[A-Za-z\\d@$!%*?&]{8}$")
    private String confirmPassword;

    //@NotNull(message = "A data de nascimento não pode ser nula.")
    //@Past(message = "A data de nascimento deve estar no passado.")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private boolean enabled = true;

    private Gender gender;

    private Phone phone;

}
