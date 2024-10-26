package com.project.figureout.model;

import com.project.figureout.validation.CpfCantBeUsedByMultipleAccounts;
import com.project.figureout.validation.EmailCantBeUsedByMultipleAccounts;
import com.project.figureout.validation.OnlyOnePreferentialCreditCard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Clientes")
@Getter @Setter
//@OnlyOnePreferentialCreditCard
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id", nullable = false, unique = true)
    private long id;

    @Column(name = "cli_nome", nullable = false, length = 80)
    private String name;

    @Column(name = "cli_email", nullable = false)
    private String email;

    @Column(name = "cli_cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "cli_senha", nullable = false, length = 128)
    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "cli_nascimento", nullable = false)
    private LocalDate birthday;

    @Column(name = "cli_ativo", nullable = false)
    private boolean enabled = true;

    // Conferir depois se precisa arrumar a(s) chave(s) estrangeira(s).
    @ManyToOne
    @JoinColumn(name = "cli_gen_id")
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cli_tel_id")
    private Phone phone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditCard> creditCards = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL)
    private List<Cart> cartList = new ArrayList<>();

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
