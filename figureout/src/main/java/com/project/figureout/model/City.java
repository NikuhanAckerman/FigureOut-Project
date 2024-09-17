package com.project.figureout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "Cidades")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid_id")
    private long id;

    @Column(name = "cid_nome", length = 37)
    private String name;

    @ManyToOne
    @JoinColumn(name = "cid_est_id")
    @JsonIgnore // prevenir loop recursivo
    private State state;


}
