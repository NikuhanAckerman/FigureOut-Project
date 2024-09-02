package com.project.figureout.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class GenderDTO {

    @NotEmpty(message = "Insira o seu gênero.")
    @Getter @Setter private String gender;

}
