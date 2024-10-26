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

    @Column(name = "trp_car_id")
    private long cartId;

    public ExchangeProductsKey(long exchangeId, long cartProductId, long cartId) {
        this.setExchangeId(exchangeId);
        this.setCartProductId(cartProductId);
        this.setCartId(cartId);
    }

    public ExchangeProductsKey() {}

}
