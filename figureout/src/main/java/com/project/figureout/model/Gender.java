package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Generos")
@Getter @Setter
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gen_id")
    private long id;

    @Column(name = "gen_genero")
    private String genderType;

    public Gender(String genderType) {
        this.genderType = genderType;
    }

    public Gender() {}

}
