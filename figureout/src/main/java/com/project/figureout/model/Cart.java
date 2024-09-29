package com.project.figureout.model;

import com.project.figureout.dto.PromotionalCouponDTO;
import com.project.figureout.service.CartService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Carrinhos")
@Getter @Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private long id;

    @Column(name = "car_preco_total")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartsProducts> cartProducts;

    @ManyToOne
    @JoinColumn(name = "car_cupom_promocional_usado")
    private PromotionalCoupon promotionalCoupon;

}
