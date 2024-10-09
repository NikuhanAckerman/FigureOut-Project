package com.project.figureout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.figureout.dto.PromotionalCouponDTO;
import com.project.figureout.service.CartService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Carrinhos")
@Getter @Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private long id;

    @Column(name = "car_sendo_usado")
    private boolean beingUsed;

    @Column(name = "data_criacao")
    private LocalDateTime dateOfCreation;

    @Column(name = "car_preco_total")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartsProducts> cartProducts;

    @ManyToOne
    @JoinColumn(name = "car_cli_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "car_cupom_promocional_usado")
    private PromotionalCoupon promotionalCoupon;

    public Cart(LocalDateTime dateOfCreation) {
        this.setDateOfCreation(dateOfCreation);
    }

    public Cart() {}

}
