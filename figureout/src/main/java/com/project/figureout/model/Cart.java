package com.project.figureout.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Carrinhos")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "car_cli_id")
    private Client client;

    @Column(name = "car_preco_total")
    private double totalPrice;

}
