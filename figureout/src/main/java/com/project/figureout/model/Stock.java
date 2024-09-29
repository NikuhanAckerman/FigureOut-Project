package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Estoque")
@Getter @Setter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "est_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "est_pro_id")
    private Product product;

    @Column(name = "est_quantidade")
    private int productQuantityAvailable;

    @Column(name = "est_valor_custo")
    private BigDecimal productPurchaseAmount;

    @ManyToOne
    @JoinColumn(name = "est_for_id") // Foreign key for the Supplier
    private Supplier supplier;

    @Column(name = "est_data_entrada")
    private LocalDateTime entryDateTime;

}
