package com.project.figureout.dto;

import com.project.figureout.model.Address;
import com.project.figureout.model.Gender;
import com.project.figureout.model.Phone;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class ClientDTO {

    private ClientBasicDataDTO clientBasicDataDTO;

    /*
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
    private Phone phone; */

    //@NotBlank(message = "O endereço de entrega não pode estar vazio.")
    private AddressDTO deliveryAddressDTO;

    //@NotBlank(message = "O endereço de cobrança não pode estar vazio.")
    private AddressDTO chargingAddressDTO;

    private boolean sameAddress = true;

}