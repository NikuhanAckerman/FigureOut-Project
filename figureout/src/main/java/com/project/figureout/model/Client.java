package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Clientes")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id")
    @Getter @Setter private long id;

    @Column(name = "cli_nome")
    @Getter @Setter private String name;

    @Column(name = "cli_email")
    @Getter @Setter private String email;

    @Column(name = "cli_telefone")
    @Getter @Setter private String phoneNumber;

    @Column(name = "cli_cpf")
    @Getter @Setter private String cpf;

    @Column(name = "cli_endereço")
    @Getter @Setter private String address;

    @Column(name = "cli_inativado")
    @Getter @Setter private boolean disabled;

}
