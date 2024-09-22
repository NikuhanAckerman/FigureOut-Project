package com.project.figureout.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class CartsProductsKey implements Serializable {

    @Column(name = "cpr_car_id")
    private long cartId;

    @Column(name = "cpr_pro_id")
    private long productId;

}
