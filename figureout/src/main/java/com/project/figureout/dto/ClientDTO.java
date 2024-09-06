package com.project.figureout.dto;

import com.project.figureout.model.Address;
import com.project.figureout.model.Gender;
import com.project.figureout.model.Phone;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class ClientDTO {

    @NotEmpty(message = "Digite o nome.")
    @Getter @Setter private String name;

    @NotEmpty(message = "Digite o email.")
    @Getter @Setter private String email;

    //@Size(min = 14, max = 14)
    @NotEmpty(message = "Digite o CPF.")
    @Getter @Setter private String cpf;

    @NotEmpty(message = "Digite uma senha.")
    @Getter @Setter private String password;

    @NotNull
    @Past(message = "A data de nascimento deve ser no passado.")
    @Getter @Setter private LocalDate birthday;

    @NotEmpty(message = "Ativo ou não?")
    @Getter @Setter private boolean enabled = true;

    @Getter @Setter private Gender gender;

    @Getter @Setter private Phone phone;

    @Getter @Setter private Address deliveryAddress;
    @Getter @Setter private Address chargingAddress;

}