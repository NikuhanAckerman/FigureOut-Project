package com.project.figureout.dto;

import com.project.figureout.model.CartsProductsKey;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter @Setter
public class ExchangeDTO {

    private HashMap<Long, Integer> cartProductQuantity;

}
