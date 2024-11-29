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
import java.util.List;

@Getter @Setter
public class StockDTO {

    private Long stockId;

    private Long product;

    @PositiveOrZero(message = "A quantidade de produtos disponíveis não pode ser menor que zero.")
    private Integer productQuantityAvailable;

    @Positive(message = "O valor de compra deve ser positivo e acima de zero.")
    private BigDecimal productPurchaseAmount;

    private List<Long> supplier;

    @PastOrPresent(message = "A data de entrada deve estar no passado ou no presente.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime entryInStockDate;

    @PastOrPresent(message = "A data de entrada deve estar no passado ou no presente.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime latestEntryDate;

}
