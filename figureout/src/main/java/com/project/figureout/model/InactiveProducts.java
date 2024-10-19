package com.project.figureout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "ina_pro_id")
    @JsonIgnore
    private Product product;

    @Column(name = "ina_razao_inativacao")
    private String reasonForInactivation;

    @Column(name = "ati_data_inativacao")
    private LocalDateTime dateTimeInactivation;
}

