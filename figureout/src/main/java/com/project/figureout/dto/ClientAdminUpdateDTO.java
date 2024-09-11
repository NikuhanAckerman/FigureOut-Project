package com.project.figureout.dto;

import com.project.figureout.model.Gender;
import com.project.figureout.model.Phone;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class ClientAdminUpdateDTO {

    @NotEmpty(message = "Digite o nome.")
    private String name;

    @NotEmpty(message = "Digite o email.")
    private String email;

    //@Size(min = 14, max = 14)
    @NotEmpty(message = "Digite o CPF.")
    private String cpf;

    @NotEmpty(message = "Digite uma senha.")
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "A data de nascimento deve ser no passado.")
    private LocalDate birthday;

    @NotEmpty(message = "Ativo ou não?")
    private boolean enabled = true;

    private Gender gender;

    private Phone phone;

}
