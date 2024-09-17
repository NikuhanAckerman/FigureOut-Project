package com.project.figureout.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenderDTO {

    @NotBlank(message = "O id do gênero não pode estar vazio.")
    private long id;

}
