package com.project.figureout.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Notificações")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id")
    private long id;

}
