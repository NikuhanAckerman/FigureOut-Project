package com.project.figureout.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Log")
@Getter @Setter
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "log_tempo")
    private LocalDateTime timestamp;

    @Column(name = "log_usuario")
    private String user;

    @Column(name = "log_acao")
    private String action;

    @Column(name = "log_detalhes")
    private String details;
}