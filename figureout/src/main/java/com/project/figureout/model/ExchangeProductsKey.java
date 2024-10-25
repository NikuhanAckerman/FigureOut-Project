package com.project.figureout.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter @Setter
public class ExchangeProductsKey implements Serializable {

    @Column(name = "trp_tro_id")
    private long exchangeId;

    @Column(name = "trp_pro_id")
    private long cartProductId;

    public ExchangeProductsKey(long exchangeId, long cartProductId) {
        this.setExchangeId(exchangeId);
        this.setCartProductId(cartProductId);
    }

    public ExchangeProductsKey() {}

}
