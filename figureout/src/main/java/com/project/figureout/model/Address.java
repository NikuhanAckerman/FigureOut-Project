package com.project.figureout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "Enderecos")
@Getter @Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "end_id")
    private long id;

    @Column(name = "end_tipo")
    private boolean addressType; // 0 = entrega, 1 = cobrança

    @Column(name = "end_apelido")
    private String nickname;
    
    @Column(name = "end_tipo_residencia")
    private String typeOfResidence;

    @Column(name = "end_logradouro")
    private String addressing;

    @Column(name = "end_numero")
    private String houseNumber;

    @Column(name = "end_bairro")
    private String neighbourhood;

    @Column(name = "end_tipo_logradouro")
    private String addressingType;

    @Column(name = "end_cep")
    private String cep;

    @Column(name = "end_cidade")
    private String city;

    @Column(name = "end_estado")
    private String state;

    @Column(name = "end_pais")
    private String country;

    @Column(name = "end_observacao")
    private String observation;

    // Conferir depois se precisa arrumar a(s) chave(s) estrangeira(s).
    @ManyToOne
    @JoinColumn(name = "end_cli_id")
    @JsonIgnore // prevenir loop recursivo
    private Client client;
}
