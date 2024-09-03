package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Telefones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tel_id")
    @Getter @Setter private long id;

    @Column(name = "tel_celular")
    @Getter @Setter private boolean cellphone;

    @Column(name = "tel_ddd")
    @Getter @Setter private String ddd;

    @Column(name = "tel_numero")
    @Getter @Setter private String phoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tel_cli_id")
    @Getter @Setter Client client;

}
