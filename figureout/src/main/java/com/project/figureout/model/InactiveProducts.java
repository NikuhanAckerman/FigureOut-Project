package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProdutosInativados")
@Getter @Setter
public class InactiveProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ina_id")
    private long id;

    @JoinColumn(name = "ina_pro_id")
    private long productId;

    @Column(name = "ina_razao_inativacao")
    private String reasonForInactivation;

    private LocalDateTime dateTimeInactivation;
}

