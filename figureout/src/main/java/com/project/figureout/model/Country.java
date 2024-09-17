package com.project.figureout.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Paises")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pai_id")
    private long id;

    @Column(name = "pai_nome", length = 6)
    private String name;


}
