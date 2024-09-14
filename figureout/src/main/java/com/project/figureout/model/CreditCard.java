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
    @Column(name = "car_id", nullable = false, unique = true)
    private long id;

    @Column(name = "car_preferido", nullable = false)
    private boolean preferido;

    @Column(name = "car_numero", nullable = false, length = 20)
    private String cardNumber;

    @Column(name = "car_nome_impresso", nullable = false, length = 30)
    private String printedName;

    @Column(name = "car_bandeira", nullable = false, length = 25)
    private String brand;

    @Column(name = "car_cod_seguranca", nullable = false, length = 4)
    private String securityCode;

    @ManyToOne
    @JoinColumn(name = "car_cli_id")
    @JsonIgnore // prevenir loop infinito
    private Client client;

}
