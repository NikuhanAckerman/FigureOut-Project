package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Endereços")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    @Getter @Setter private long id;

    @Column(name = "end_cli_id")
    @Getter @Setter private long cli_id;

    @Column(name = "end_cep")
    @Getter @Setter private String cep;

    @Column(name = "end_estado")
    @Getter @Setter private String estado;

    @Column(name = "end_cidade")
    @Getter @Setter private String cidade;

    @Column(name = "end_bairro")
    @Getter @Setter private String bairro;

    @Column(name = "end_rua")
    @Getter @Setter private String rua;

    @Column(name = "end_numero")
    @Getter @Setter private String numero;

    @Column(name = "end_complemento")
    @Getter @Setter private String complemento;
}
