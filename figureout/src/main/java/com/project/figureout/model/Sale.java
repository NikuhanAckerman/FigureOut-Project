package com.project.figureout.model;

import com.project.figureout.dto.PromotionalCouponDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Vendas")
@Getter @Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ven_id")
    private long id;

    @Column(name = "ven_preco_final")
    private BigDecimal finalPrice;

    @ManyToOne
    @JoinColumn(name = "ven_car_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ven_end_id")
    private Address deliveryAddress;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesCards> cardsUsedInThisSale = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    private SaleStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "ven_cupom_promocional_aplicado")
    private PromotionalCoupon promotionalCouponApplied;

    @JoinColumn(name = "ven_frete")
    private BigDecimal freight;

}
