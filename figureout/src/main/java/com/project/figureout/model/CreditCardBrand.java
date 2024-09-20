package com.project.figureout.model;

import jakarta.persistence.*;

@Entity
@Table(name = "BandeirasCartoes")
public class CreditCardBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ban_id", nullable = false, unique = true)
    private long id;

    @Column(name = "ban_nome", nullable = false, unique = true)
    private String name;

    public CreditCardBrand(String name) {
        this.name = name;
    }

    public CreditCardBrand() {}

}
