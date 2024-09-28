package com.project.figureout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CuponsPromocionais")
@Getter @Setter
public class PromotionalCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cup_id")
    private long id;

    @Column(name = "cup_nome")
    private String couponName;

    @Column(name = "cup_desconto")
    private double couponDiscount;

    public PromotionalCoupon(String couponName, double couponDiscount) {
        this.setCouponName(couponName);
        this.setCouponDiscount(couponDiscount);
    }

    public PromotionalCoupon() {}

}
