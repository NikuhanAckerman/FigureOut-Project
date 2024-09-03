package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Table(name = "Generos")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id")
    @Getter @Setter private long id;

    @Column(name = "gen_genero")
    @Getter @Setter private String gender;

}
