package com.project.figureout.dto;

import com.project.figureout.model.Cart;
import com.project.figureout.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class CartProductDTO {

    private Cart cart;

    private Product product;

    private int productQuantity;

    private LocalDateTime productAddedTime;

    private BigDecimal priceToPay;

}
