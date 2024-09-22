package com.project.figureout.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GruposDePrecificacao")
public class PricingGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gdp_id")
    private long id;

    @Column(name = "gdp_nome")
    private String name;

    @Column(name = "gdp_percentual")
    private double percentage;
}
