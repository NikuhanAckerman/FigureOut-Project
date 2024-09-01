package com.project.figureout.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Cartoes")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    @Getter @Setter private long id;

    @Column(name = "car_nome_impresso")
    @Getter @Setter private String printedName;

    @Column(name = "car_numero")
    @Getter @Setter private String cardNumber;

    @Column(name = "car_cod_seguranca")
    @Getter @Setter private String securityCode;

    @Column(name = "car_validade")
    @Getter @Setter private Date expiration;

    @Column(name = "car_pais")
    @Getter @Setter private String country;

    @Column(name = "car_cli_id")
    @Getter @Setter private long cli_id;
    
    @Column(name = "car_ban_id")
    @Getter @Setter private long ban_id;
}
