package com.project.figureout.dto;

import com.project.figureout.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SaleDTO {

    private List<Long> salesCardsIds;

    private long saleCartId;

    private long deliveryAddressId;

}
