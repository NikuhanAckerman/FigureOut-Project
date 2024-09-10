package com.project.figureout.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    @NotEmpty(message = "Insira o tipo")
    private boolean addressType;

    @NotEmpty(message = "Insira o apelido")
    private String nickname;

    @NotEmpty(message = "Insira o tipo de residência(Casa, apartamento, etc...)")
    private String typeOfResidence;

    @NotEmpty(message = "Insira o seu logradouro.")
    private String addressing;

    @NotEmpty(message = "Insira o número da sua residência")
    //@Size(min = 1, max = 5)
    private String houseNumber;

    @NotEmpty(message = "Insira o seu bairro")
    private String neighbourhood;

    @NotEmpty(message = "Insira o seu tipo de logradouro")
    private String addressingType;

    @NotEmpty(message = "Insira o seu CEP.")
    private String cep;

    @NotEmpty(message = "Insira a sua cidade.")
    private String city;

    @NotEmpty(message = "Insira o seu Estado")
    private String state;

    @NotEmpty(message = "Insira o seu país")
    private String country;

    @NotEmpty(message = "Insira uma observação (opcional)")
    private String observation;


}
