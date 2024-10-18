package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProdutosAtivados")
@Getter @Setter
public class ActiveProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ina_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "ati_pro_id")
    private Product product;

    @Column(name = "ati_razao_ativacao")
    private String reasonForActivation;

    @Column(name = "ati_data_ativacao")
    private LocalDateTime dateTimeActivation;

}
