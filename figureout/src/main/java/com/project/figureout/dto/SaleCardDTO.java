package com.project.figureout.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
public class SaleCardDTO {

    @NotNull
    @PositiveOrZero(message = "A quantidade paga deve ser maior ou igual a zero.")
    HashMap<Long, BigDecimal> amountPaid = new HashMap<>();

}
