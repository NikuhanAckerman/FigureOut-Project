package com.project.figureout.dto;

import com.project.figureout.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SaleDTO {

    private List<SalesCards> salesCards;

    private Cart saleCart;

    private Address deliveryAddress;

}
