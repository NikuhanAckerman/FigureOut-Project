package com.project.figureout.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter
public class MultipleCartProductDTO {
    // ID of the product, and quantity desired
    private HashMap<Long, Integer> productQuantities;

}
