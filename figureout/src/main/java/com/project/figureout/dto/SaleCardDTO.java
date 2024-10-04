package com.project.figureout.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
public class SaleCardDTO {

    private long cardId;

    private BigDecimal amountPaid;

}
