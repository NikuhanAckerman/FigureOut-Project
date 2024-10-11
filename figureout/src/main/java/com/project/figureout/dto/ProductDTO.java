package com.project.figureout.dto;

import com.project.figureout.model.Category;
import com.project.figureout.model.PricingGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
public class ProductDTO {

    private boolean active;

    private String reasonForInactivation;

    private String name;

    private String description;

    private float height;

    private float width;

    private float weight;

    private float length;

    private BigDecimal purchaseAmount;

    private List<Long> categoriesIds;

    private long pricingGroup;

    private BigDecimal price;

    private MultipartFile productImage;

    private StockDTO stockDTO;

    private long supplier;

}
