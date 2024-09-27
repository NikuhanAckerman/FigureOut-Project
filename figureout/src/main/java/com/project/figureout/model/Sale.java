package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Vendas")
@Getter @Setter
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ven_id")
    private long id;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Address deliveryAddress;

    @ManyToMany
    @JoinTable(name = "VendasCartoesDeCredito",
            joinColumns = @JoinColumn(name = "ven_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private List<CreditCard> creditCardList = new ArrayList<>();

    private enum Status {
        PAGAMENTO_REJEITADO,
        PAGAMENTO_REALIZADO,
        EM_PROCESSAMENTO,
        EM_TRANSPORTE,
        ENTREGUE
    }

    @Enumerated(EnumType.ORDINAL)
    private Status status;

}
