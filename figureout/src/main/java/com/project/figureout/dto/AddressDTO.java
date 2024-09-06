package com.project.figureout.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class AddressDTO {

    @NotEmpty(message = "Insira o apelido")
    @Getter @Setter private String nickname;

    @NotEmpty(message = "Insira o tipo")
    @Getter @Setter private boolean addressType;

    @NotEmpty(message = "Insira o tipo de residência(Casa, apartamento, etc...)")
    @Getter @Setter private String typeOfResidence;

    @NotEmpty(message = "Insira o seu logradouro.")
    @Getter @Setter private String addressing;

    @NotEmpty(message = "Insira o número da sua residência")
    //@Size(min = 1, max = 5)
    @Getter @Setter private String houseNumber;

    @NotEmpty(message = "Insira o seu bairro")
    @Getter @Setter private String neighbourhood;

    @NotEmpty(message = "Insira o seu tipo de logradouro")
    @Getter @Setter private String addressingType;

    @NotEmpty(message = "Insira o seu CEP.")
    @Getter @Setter private String cep;

    @NotEmpty(message = "Insira a sua cidade.")
    @Getter @Setter private String city;

    @NotEmpty(message = "Insira o seu Estado")
    @Getter @Setter private String state;

    @NotEmpty(message = "Insira o seu país")
    @Getter @Setter private String country;

    @NotEmpty(message = "Insira uma observação (opcional)")
    @Getter @Setter private String observation;


}
