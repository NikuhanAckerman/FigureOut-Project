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

    @Column(name = "cli_cpf")
    @Getter @Setter private String cpf;
    
    // É bom tirar, tem as outras colunas pra telefone.
    //@Column(name = "cli_telefone")
    //@Getter @Setter private String phoneNumber;

    @Column(name = "cli_senha")
    @Getter @Setter private String password;

    @Column(name = "cli_nascimento")
    @Getter @Setter private Date birthday;
    
    // Melhor tirar isso aqui, tipo, essa coluna nem existe mais no outro schema.
    //@Column(name = "cli_endereco")
    //@Getter @Setter private String address;
    
    // Mudei o nome pro novo schema "FigureOutV1". Recomendo trocar o nome do atributo também se você quiser :p
    @Column(name = "cli_ativo")
    @Getter @Setter private boolean disabled;

    @Column(name = "cli_tra_id")
    @Getter @Setter private long tra_id;

    @Column(name = "cli_gen_id")
    @Getter @Setter private long gen_id;

    @Column(name = "cli_tel_id")
    @Getter @Setter private long tel_id;
}
