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

    @Column(name = "end_entrega", nullable = false)
    private boolean deliveryAddress; // 0 = entrega, 1 = cobrança

    @Column(name = "end_cobranca", nullable = false)
    private boolean chargingAddress;

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

    @Column(name = "end_cidade", nullable = false, length = 32) //Vila Bela da Santíssima Trindade
    private String city;

    @Column(name = "end_estado", nullable = false, length = 19) //Rio Grande do Norte
    private String state;

    @Column(name = "end_pais", nullable = false, length = 6) // Brasil
    private String country;

    @Column(name = "end_observacao", length = 60)
    private String observation;

    // Conferir depois se precisa arrumar a(s) chave(s) estrangeira(s).
    @ManyToOne
    @JoinColumn(name = "end_cli_id")
    @JsonIgnore // prevenir loop recursivo
    private Client client;
}
