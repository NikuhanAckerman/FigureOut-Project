package com.project.figureout.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PhoneDTO {

    @NotNull(message = "O tipo de telefone não pode ser nulo.")
    private boolean cellphone;

    @NotBlank(message = "O DDD não pode ser nulo.")
    @Pattern(regexp = "^[\\d]{2}$")
    private String ddd;

    @NotBlank(message = "O número de telefone não pode ser nulo.")
    @Pattern(regexp = "^[\\d-]{1,10}$")
    //@Size(min = 8, max = 10)
    // 91234-5678
    // 1234-5678
    private String phoneNumber;

}
