package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CarrinhosProdutos")
@Getter @Setter
public class CartsProducts {

    @EmbeddedId
    CartsProductsKey id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cpr_car_id")
    Cart cart;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "cpr_pro_id")
    Product product;

    @Column(name = "cpr_quantidade_produto")
    private int productQuantity;

    @Column(name = "cpr_datahora_produto_adicionado")
    private LocalDateTime productAddedTime;

    @Column(name = "cpr_preco_produto")
    private double productPrice;
}
