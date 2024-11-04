package com.project.figureout.dto;

import com.project.figureout.model.Sale;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class ProductInChartDTO {

    private long productId;

    private String name;

    private BigDecimal valuePurchased;

    private Integer volumePurchased;

}
