package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Getter @Setter private boolean enabled = true;

    // Conferir depois se precisa arrumar a(s) chave(s) estrangeira(s).
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cli_gen_id")
    @Getter @Setter Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "cli_tel_id")
    @Getter @Setter Phone phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private ArrayList<Address> addresses;

    // Expressão regular para validar a senha
    // (Incluir no mínimo 8 caracteres, letra mínuscula, maiúscula e caractere especial).
    //private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    //public boolean isValidPassword(String password) {
    //    if (password == null) {
    //        return false;
    //    }
    //    return password.matches(PASSWORD_PATTERN);
    //}
}
