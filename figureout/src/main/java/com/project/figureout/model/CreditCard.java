package com.project.figureout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Cartoes")
@Getter @Setter
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private long id;

    @Column(name = "car_preferido")
    private boolean preferido;

    @Column(name = "car_numero")
    private String cardNumber;

    @Column(name = "car_nome_impresso")
    private String printedName;

    @Column(name = "car_bandeira")
    private String brand;

    @Column(name = "car_cod_seguranca")
    private String securityCode;

    //@Column(name = "car_validade")
    //@Getter @Setter private Date expiration;

    // Conferir depois se precisa arrumar a(s) chave(s) estrangeira(s).
    @ManyToOne
    @JoinColumn(name = "car_cli_id")
    @JsonIgnore // prevenir loop infinito
    private Client client;

    //@Column(name = "car_ban_id")
    //@Getter @Setter private long ban_id;
}
