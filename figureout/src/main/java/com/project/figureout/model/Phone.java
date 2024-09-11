package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Telefones")
@Getter @Setter
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tel_id")
    private long id;

    @Column(name = "tel_celular")
    private boolean cellphone;

    @Column(name = "tel_ddd")
    private String ddd;

    @Column(name = "tel_numero")
    private String phoneNumber;

}
