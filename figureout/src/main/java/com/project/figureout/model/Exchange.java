package com.project.figureout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Trocas")
@Getter @Setter
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tro_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "tro_cli_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "tro_ven_id")
    @JsonIgnore
    private Sale sale;

    @OneToMany(mappedBy = "exchange", cascade = CascadeType.ALL)
    @JoinColumn(name = "tro_trp_id")
    private List<ExchangeProducts> returnedProducts;

    @Column(name = "tro_data_hora_requisicao")
    private LocalDateTime exchangeRequestTime;

    @Column(name = "tro_data_hora_aceita")
    private LocalDateTime exchangeAcceptedTime;

    @Column(name = "tro_data_hora_finalizada")
    private LocalDateTime exchangeFinishTime;

}
