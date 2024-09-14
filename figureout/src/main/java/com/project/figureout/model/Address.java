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
    @Column(name = "end_id", nullable = false, unique = true)
    private long id;

    @Column(name = "end_tipo", nullable = false)
    private boolean addressType; // 0 = entrega, 1 = cobrança

    @Column(name = "end_apelido", nullable = false, length = 30)
    private String nickname;
    
    @Column(name = "end_tipo_residencia", nullable = false, length = 30)
    private String typeOfResidence;

    @Column(name = "end_logradouro", nullable = false, length = 50)
    private String addressing;

    @Column(name = "end_numero", nullable = false)
    private String houseNumber;

    @Column(name = "end_bairro", nullable = false, length = 50)
    private String neighbourhood;

    @Column(name = "end_tipo_logradouro", nullable = false, length = 15)
    private String addressingType;

    @Column(name = "end_cep", nullable = false, length = 8)
    private String cep;

    @Column(name = "end_cidade", nullable = false, length = 187)
    private String city;

    @Column(name = "end_estado", nullable = false, length = 19)
    private String state;

    @Column(name = "end_pais", nullable = false, length = 60)
    private String country;

    @Column(name = "end_observacao", length = 60)
    private String observation;

    // Conferir depois se precisa arrumar a(s) chave(s) estrangeira(s).
    @ManyToOne
    @JoinColumn(name = "end_cli_id")
    @JsonIgnore // prevenir loop recursivo
    private Client client;
}
