package com.project.figureout.service;

import com.project.figureout.dto.ClientBasicDataDTO;
import com.project.figureout.dto.ProductDTO;
import com.project.figureout.model.*;
import com.project.figureout.repository.CategoryRepository;
import com.project.figureout.repository.PricingGroupRepository;
import com.project.figureout.repository.ProductRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PricingGroupRepository pricingGroupRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Produto não encontrado com base no ID."));
    }

    public void deleteProductById(long id) {
        productRepository.deleteById(id); // add exception throwing to this later, apparently this doesnt throw EmptyResultDataAccessException anymore
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void productDataSetter(Product product, ProductDTO productDTO) {
        System.out.println("calling product data setter");
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setHeight(productDTO.getHeight());
        product.setWidth(productDTO.getWidth());
        product.setWeight(productDTO.getWeight());
        product.setLength(productDTO.getWidth());
        product.setPurchaseAmount(productDTO.getPurchaseAmount());

        List<Category> categoryListAddToProduct = new ArrayList<>();
        categoryListAddToProduct.addAll(categoryRepository.findAllById(productDTO.getCategoriesIds()));

        product.setCategories(categoryListAddToProduct);

        product.setPricingGroup(pricingGroupRepository.findById(productDTO.getPricingGroup()).orElseThrow(() -> new NoSuchElementException("Grupo de precificação não encontrado.")));

        product.setImageFilepath(saveProductPicture(productDTO));

    }

    public String saveProductPicture(ProductDTO productDTO) {
        MultipartFile multipartFile = productDTO.getProductImage();
        String fileName = multipartFile.getOriginalFilename();

        try {
            String uploadDirectory = "/Product_Images";
            Path uploadPath = Paths.get(uploadDirectory);

            if(!Files.exists(uploadPath)) {
                Files.createDirectory(uploadPath);
            }

            try(InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDirectory + fileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileName;

    }

}
