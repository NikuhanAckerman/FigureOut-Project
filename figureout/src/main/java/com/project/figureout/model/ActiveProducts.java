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

    @JoinColumn(name = "ati_pro_id")
    private long productId;

    @Column(name = "ati_razao_ativacao")
    private String reasonForActivation;

    private LocalDateTime dateTimeActivation;

}
