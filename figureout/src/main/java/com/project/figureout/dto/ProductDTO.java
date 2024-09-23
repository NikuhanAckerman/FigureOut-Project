package com.project.figureout.dto;

import com.project.figureout.model.Category;
import com.project.figureout.model.PricingGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class ProductDTO {

    private String name;

    private String description;

    private float height;

    private float width;

    private float weight;

    private float length;

    private double purchaseAmount;

    private List<Long> categoriesIds;

    private long pricingGroup;

    private MultipartFile productImage;

}
