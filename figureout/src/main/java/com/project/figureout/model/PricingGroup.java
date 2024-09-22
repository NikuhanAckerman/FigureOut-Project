package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GruposDePrecificacao")
@Getter @Setter
public class PricingGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gdp_id")
    private long id;

    @Column(name = "gdp_nome")
    private String name;

    @Column(name = "gdp_percentual")
    private double percentage;

    public PricingGroup(String name, double percentage) {
        this.setName(name);
        this.setPercentage(percentage);
    }

    public PricingGroup() {}
}
