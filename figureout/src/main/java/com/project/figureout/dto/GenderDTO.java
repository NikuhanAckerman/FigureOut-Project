package com.project.figureout.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class GenderDTO {

    @NotEmpty(message = "Insira o seu gênero.")
    @Getter @Setter private String genderType;

}
