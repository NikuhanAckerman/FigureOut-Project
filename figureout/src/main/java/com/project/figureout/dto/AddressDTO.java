package com.project.figureout.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressDTO {

    private boolean deliveryAddress = false;

    private boolean chargingAddress = false;

    @NotEmpty(message = "Insira o apelido")
    private String nickname;

    @NotEmpty(message = "Insira o tipo de residência(Casa, apartamento, etc...)")
    @Pattern(regexp = "^[A-Za-z\\s]*$") // letras, numeros, espaços
    private String typeOfResidence;

    @NotEmpty(message = "Insira o seu logradouro.")
    @Pattern(regexp = "^[A-Za-z\\d\\s]*$")
    private String addressing;

    @NotEmpty(message = "Insira o número da sua residência")
    //@Size(min = 1, max = 5)
    @Pattern(regexp = "^[\\d]{1,5}$") // 1 a 5 caracteres, apenas numeros
    private String houseNumber;

    @NotEmpty(message = "Insira o seu bairro")
    @Pattern(regexp = "^[A-Za-z\\d\\s]*$")
    private String neighbourhood;

    @NotEmpty(message = "Insira o seu tipo de logradouro")
    @Pattern(regexp = "^[A-Za-z\\s]*$")
    private String addressingType;

    @NotEmpty(message = "Insira o seu CEP.")
    @Pattern(regexp = "^\\d{5}-\\d{3}$") // 5 digitos, traço, 3 digitos
    private String cep;

    @NotEmpty(message = "Insira a sua cidade.")
    @Pattern(regexp = "^[A-Za-z\\s]*$")
    private String city;

    @NotEmpty(message = "Insira o seu Estado")
    @Pattern(regexp = "^[A-Za-z\\s]*$")
    private String state;

    @NotEmpty(message = "Insira o seu país")
    @Pattern(regexp = "^[A-Za-z\\s]*$")
    private String country;

    @NotEmpty(message = "Insira uma observação (opcional)")
    private String observation;

}
