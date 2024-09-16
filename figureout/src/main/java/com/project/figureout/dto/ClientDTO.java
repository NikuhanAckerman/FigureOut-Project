package com.project.figureout.dto;

import com.project.figureout.model.Address;
import com.project.figureout.model.Gender;
import com.project.figureout.model.Phone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
public class ClientDTO {

    @Valid
    private ClientBasicDataDTO clientBasicDataDTO;

    //@NotBlank(message = "O endereço de entrega não pode estar vazio.")
    private AddressDTO addressDTO;

}