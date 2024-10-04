package com.project.figureout.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;

@Getter @Setter
public class SaleCardDTO {
    private HashMap<Long, BigDecimal> idAmountPaid;
}
