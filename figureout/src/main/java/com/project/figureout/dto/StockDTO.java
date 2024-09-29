package com.project.figureout.dto;

import com.project.figureout.model.Product;
import com.project.figureout.model.Supplier;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class StockDTO {

    private int productQuantityAvailable;

    private LocalDate entryInStockDate;

}
