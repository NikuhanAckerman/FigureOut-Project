package com.project.figureout.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter
public class MultipleCartProductDTO {

    // ID of the product, and quantity desired
    @NotNull
    @Positive(message = "A quantidade dos produtos deve ser maior que zero.")
    private HashMap<Long, Integer> productQuantities;

}
