package com.project.figureout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Estados")
@Getter @Setter
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "est_id")
    private long id;

    @Column(name = "est_nome", length = 25)
    private String name;

    @Column(name = "est_frete")
    private double frete;

    public State(String name, double frete) {
        this.setName(name);
        this.setFrete(frete);
    }

    public State() {}

}
