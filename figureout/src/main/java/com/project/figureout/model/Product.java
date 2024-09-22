package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Produtos")
@Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private long id;

    @Column(name = "pro_nome")
    private String name;

    @Column(name = "pro_descricao")
    private String description;

    @Column(name = "pro_altura")
    private float height;

    @Column(name = "pro_largura")
    private float width;

    @Column(name = "pro_peso")
    private float weight;

    @Column(name = "pro_profundidade")
    private float depth;

    @ManyToMany
    @JoinTable(
            name = "ProdutosCategorias",
            joinColumns = @JoinColumn(name = "pro_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id")
    )
    private List<Category> categories;



}
