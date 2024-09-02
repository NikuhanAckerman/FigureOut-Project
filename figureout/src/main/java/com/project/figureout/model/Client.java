package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    @Column(name = "cli_cpf")
    @Getter @Setter private String cpf;

    @Column(name = "cli_senha")
    @Getter @Setter private String password;

    @Column(name = "cli_nascimento")
    @Getter @Setter private Date birthday;

    @Column(name = "cli_ativo")
    @Getter @Setter private boolean disabled;

    @Column(name = "cli_tra_id")
    @Getter @Setter private long tra_id;

    @Column(name = "cli_gen_id")
    @Getter @Setter private long gen_id;

    @Column(name = "cli_tel_id")
    @Getter @Setter private long tel_id;
}
