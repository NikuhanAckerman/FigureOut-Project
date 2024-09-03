package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.util.Set;

@Entity
@DynamicInsert
@Table(name = "Enderecos")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    @Getter @Setter private long id;
    
    @Column(name = "end_tipo_residencia")
    @Getter @Setter private String typeOfResidence;

    @Column(name = "end_logradouro")
    @Getter @Setter private String adressing;

    @Column(name = "end_numero")
    @Getter @Setter private String houseNumber;

    @Column(name = "end_bairro")
    @Getter @Setter private String neighbourhood;

    @Column(name = "end_tipo_logradouro")
    @Getter @Setter private String addressingType;

    @Column(name = "end_cep")
    @Getter @Setter private String cep;

    @Column(name = "end_cidade")
    @Getter @Setter private String city;

    @Column(name = "end_estado")
    @Getter @Setter private String state;

    @Column(name = "end_pais")
    @Getter @Setter private String country;

    @Column(name = "end_observacao")
    @Getter @Setter private String observation;

    // Conferir depois se precisa arrumar a(s) chave(s) estrangeira(s).
    @ManyToOne
    @JoinColumn(name = "end_cli_id")
    @Getter @Setter Client clients;
}
