package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "CuponsDeTroca")
@Getter @Setter
public class ExchangeCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cdt_id")
    private long id;

    @Column(name = "cdt_codigo")
    private String exchangeCouponCode;

    @ManyToOne
    @JoinColumn(name = "cdt_cli_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "cdt_tro_id")
    private Exchange exchange;

    @Column(name = "cdt_quantia")
    private BigDecimal amount;

}
