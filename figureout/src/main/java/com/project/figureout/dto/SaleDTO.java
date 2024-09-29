package com.project.figureout.dto;

import com.project.figureout.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SaleDTO {

    private List<CartProductDTO> cartProductDTO;

    private PromotionalCouponDTO promotionalCouponDTO;

}
