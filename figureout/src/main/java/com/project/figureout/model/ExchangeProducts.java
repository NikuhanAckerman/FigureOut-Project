package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "TrocasProdutosDevolvidos")
@Getter @Setter
public class ExchangeProducts {

    @EmbeddedId
    private ExchangeProductsKey id;

    @MapsId
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trp_tro_id")
    private Exchange exchange;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "trp_cpr_id")
    private CartsProducts cartProduct;

    @Column(name = "trp_quantidade")
    private int quantityReturned;

}
