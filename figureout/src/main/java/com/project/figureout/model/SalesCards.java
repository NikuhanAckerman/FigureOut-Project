package com.project.figureout.model;

import jakarta.persistence.*;

@Entity
@Table(name = "VendasCartoesDeCredito")
public class SalesCards {

    @EmbeddedId
    private SalesCardsKey id;

    @ManyToOne
    @MapsId("saleId")
    @JoinColumn(name = "vdc_ven_id")
    private Sale sale;

    @ManyToOne
    @MapsId("creditCardId")
    @JoinColumn(name = "vdc_cre_id")
    private CreditCard creditCard;

    @Column(name = "vdc_valor_pago")
    private double amountPaid;

}
