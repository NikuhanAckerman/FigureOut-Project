package com.project.figureout.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Categorias")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private long id;

    @Column(name = "cat_nome")
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public Category() {}

}
