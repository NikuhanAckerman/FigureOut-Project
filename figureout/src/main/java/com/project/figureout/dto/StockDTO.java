package com.project.figureout.dto;

import com.project.figureout.model.Product;
import com.project.figureout.model.Supplier;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class StockDTO {

    private Long product;

    @NotNull(message = "O campo de produtos disponíveis não pode ser nulo.")
    @PositiveOrZero(message = "A quantidade de produtos disponíveis não pode ser menor que zero.")
    private Integer productQuantityAvailable;

    private BigDecimal productPurchaseAmount;

    private Long supplier;

    @PastOrPresent(message = "A data de entrada deve estar no passado ou no presente.")
    @NotNull(message = "O campo de data de entrada não pode ser nulo.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryInStockDate;

}
